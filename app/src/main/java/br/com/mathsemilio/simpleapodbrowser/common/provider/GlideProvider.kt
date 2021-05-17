package br.com.mathsemilio.simpleapodbrowser.common.provider

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GlideProvider {

    fun loadResourceFromUrl(url: String, targetImageView: ImageView) {
        Glide.with(targetImageView)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(targetImageView)
    }

    suspend fun clearLocalCachedImages(context: Context) {
        withContext(Dispatchers.IO) {
            Glide.get(context).clearDiskCache()
        }
    }
}