/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.simpleapodbrowser.ui.common.manager

import kotlinx.coroutines.*
import android.graphics.Color
import android.content.Context
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.graphics.drawable.ColorDrawable
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object ImageResourceManager {

    fun loadFrom(url: String, targetImageView: ImageView) {
        Glide.with(targetImageView)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(targetImageView)
    }

    fun loadWithPlaceholderFrom(url: String, targetImageView: ImageView) {
        Glide.with(targetImageView)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(ColorDrawable(Color.BLACK))
            .error(ColorDrawable(Color.BLACK))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(targetImageView)
    }

    suspend fun clearLocalCache(context: Context) {
        withContext(Dispatchers.IO) {
            Glide.get(context).clearDiskCache()
        }
    }
}
