package ru.geekbrains.github_client.ui.image

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.rxjava3.core.Scheduler
import ru.geekbrains.github_client.mvp.model.cache.IImageCache
import ru.geekbrains.github_client.mvp.model.image.IImageLoader
import ru.geekbrains.github_client.mvp.model.network.INetworkStatus
import java.io.ByteArrayOutputStream

class GlideImageLoader(
    private val networkStatus: INetworkStatus,
    private val cache: IImageCache,
    private val mainThread: Scheduler
) : IImageLoader<ImageView> {

    override fun load(url: String, container: ImageView) {
        networkStatus.isOnlineSingle()
            .observeOn(mainThread)
            .subscribe { isOnlinne ->
                if (isOnlinne) {
                    Glide.with(container.context)
                        .asBitmap()
                        .load(url)
                        .listener(object : RequestListener<Bitmap> {
                            override fun onLoadFailed(
                                e: GlideException?, model: Any?,
                                target: Target<Bitmap>?, isFirstResource: Boolean
                            ): Boolean {
                                return true
                            }

                            override fun onResourceReady(
                                resource: Bitmap?, model: Any?, target: Target<Bitmap>?,
                                dataSource: DataSource?, isFirstResource: Boolean
                            ): Boolean {
                                val compressFormat =
                                    if (url.contains(".jpg")) Bitmap.CompressFormat.JPEG else Bitmap.CompressFormat.PNG
                                val stream = ByteArrayOutputStream()
                                resource?.compress(compressFormat, 100, stream)
                                val bytes = stream.use { it.toByteArray() }
                                cache.putImage(url, bytes).blockingAwait()
                                return false
                            }
                        })
                        .into(container)
                } else {
                    cache.getImage(url)
                        .observeOn(mainThread)
                        .subscribe({ byteArray ->
                            Glide.with(container.context).load(byteArray).into(container)
                        }, { exception ->
                            exception.printStackTrace()
                        })
                }
            }
    }
}