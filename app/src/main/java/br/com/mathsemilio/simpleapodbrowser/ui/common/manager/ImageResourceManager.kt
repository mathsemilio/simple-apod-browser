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

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ImageResourceManager {

    fun loadFrom(url: String, targetImageView: ImageView) {
        Glide.with(targetImageView)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(targetImageView)
    }

    suspend fun clearLocalCache(context: Context) {
        withContext(Dispatchers.IO) {
            Glide.get(context).clearDiskCache()
        }
    }
}
