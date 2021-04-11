package ru.geekbrains.github_client.mvp.model.cache.room

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.github_client.mvp.model.cache.IImageCache
import ru.geekbrains.github_client.mvp.model.entity.room.RoomCachedImage
import ru.geekbrains.github_client.mvp.model.entity.room.db.Database
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class RoomImageCache(private val db: Database, private val dir: File) : IImageCache {

    override fun getImage(url: String) = Maybe.fromCallable {
        db.imageDao.findByUrl(url)?.let {
            File(it.localPath).inputStream().readBytes()
        }
    }.subscribeOn(Schedulers.io())

    override fun putImage(url: String, bytes: ByteArray) = Completable.create { emitter ->
        if (!dir.exists() && !dir.mkdir()) {
            emitter.onError(IOException("Failed to create cache dir"))
            return@create
        }

        val fileFormat = if (url.contains(".jpg")) ".jpg" else ".png"
        val imageFile = File(dir, UUID.nameUUIDFromBytes(bytes).toString() + fileFormat)
        try {
            FileOutputStream(imageFile).use {
                it.write(bytes)
            }
        } catch (e: Exception) {
            emitter.onError(e)
        }
        db.imageDao.insert(RoomCachedImage(url, imageFile.path))
        emitter.onComplete()
    }.subscribeOn(Schedulers.io())

    override fun contains(url: String) =
        Single.fromCallable { db.imageDao.findByUrl(url) != null }.subscribeOn(Schedulers.io())

    override fun clear() = Completable.fromAction {
        db.imageDao.clear()
        dir.deleteRecursively()
    }.subscribeOn(Schedulers.io())
}