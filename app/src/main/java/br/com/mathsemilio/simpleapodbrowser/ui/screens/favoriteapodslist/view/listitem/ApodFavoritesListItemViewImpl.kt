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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.favoriteapodslist.view.listitem

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_IMAGE
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_VIDEO
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_APOD_TYPE_EXCEPTION
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.ImageResourceManager
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod

class ApodFavoritesListItemViewImpl(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : ApodFavoritesListItemView() {

    private lateinit var imageViewApod: ImageView
    private lateinit var textViewApodTitle: TextView
    private lateinit var textViewApodType: TextView

    private lateinit var favoriteApod: Apod

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_item, parent, false)

        initializeViews()

        attachApodListItemOnClickListener()
    }

    private fun initializeViews() {
        imageViewApod = rootView.findViewById(R.id.image_view_apod)
        textViewApodTitle = rootView.findViewById(R.id.text_view_apod_title)
        textViewApodType = rootView.findViewById(R.id.text_view_apod_type)
    }

    private fun attachApodListItemOnClickListener() {
        rootView.setOnClickListener {
            notify { listener ->
                listener.onFavoriteApodClicked(favoriteApod)
            }
        }
    }

    override fun bind(favoriteApod: Apod) {
        this.favoriteApod = favoriteApod

        ImageResourceManager.loadFrom(favoriteApod.url, imageViewApod)

        loadApodVideoThumbnail()

        textViewApodTitle.text = favoriteApod.title

        setApodType(favoriteApod.mediaType)
    }

    private fun loadApodVideoThumbnail() {
        favoriteApod.thumbnailUrl?.let { url ->
            ImageResourceManager.loadFrom(url, imageViewApod)
        }
    }

    private fun setApodType(mediaType: String) {
        textViewApodType.text = when (mediaType) {
            APOD_TYPE_IMAGE -> getString(R.string.apod_type_image)
            APOD_TYPE_VIDEO -> getString(R.string.apod_type_video)
            else -> throw IllegalArgumentException(ILLEGAL_APOD_TYPE_EXCEPTION)
        }
    }
}
