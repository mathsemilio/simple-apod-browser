package br.com.mathsemilio.simpleapodbrowser.common.provider

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object GlideProvider {

    fun loadResourceFromUrl(url: String, targetImageView: ImageView) {
        Glide.with(targetImageView)
            .load(url)
            .thumbnail(0.50F)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(targetImageView)
    }
}