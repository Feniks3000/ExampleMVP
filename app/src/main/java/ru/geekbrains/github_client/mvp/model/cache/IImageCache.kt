package ru.geekbrains.github_client.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface IImageCache {
    fun getImage(url: String): Maybe<ByteArray?>
    fun putImage(url: String, bytes: ByteArray): Completable
    fun contains(url: String): Single<Boolean>
    fun clear(): Completable
}