package br.com.mathsemilio.simpleapodbrowser.common.provider

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class GlideProvider(private val context: Context) {

    fun loadResourceFromUrl(url: String, targetImageView: ImageView) {
        Glide.with(context)
            .load(url)
            .thumbnail(0.50F)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(targetImageView)
    }
}