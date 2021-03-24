package br.com.mathsemilio.simpleapodbrowser.common.provider

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class GlideProvider(private val context: Context) {

    private val baseResourceRequestOptions = RequestOptions()
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    fun loadResourceFromUrl(url: String, targetImageView: ImageView) {
        Glide.with(context)
            .load(url)
            .thumbnail(0.50F)
            .apply(baseResourceRequestOptions)
            .into(targetImageView)
    }
}