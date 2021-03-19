package br.com.mathsemilio.simpleapodbrowser.common.provider

import android.content.Context
import android.widget.ImageView
import br.com.mathsemilio.simpleapodbrowser.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class GlideProvider(private val context: Context) {

    fun loadResourceFromUrl(url: String, targetImageView: ImageView) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_broken_image_24)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(targetImageView)
    }
}