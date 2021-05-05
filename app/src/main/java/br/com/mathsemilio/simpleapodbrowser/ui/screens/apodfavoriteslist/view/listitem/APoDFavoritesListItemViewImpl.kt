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
package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.listitem

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

class APoDFavoritesListItemViewImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    APoDFavoritesListItemView() {

    private lateinit var imageViewAPoDFavoritesListItemImage: ImageView
    private lateinit var textViewAPoDFavoritesListItemTitle: TextView
    private lateinit var imageViewRemoveFromFavorites: ImageView

    private lateinit var currentFavoriteAPoD: APoD

    init {
        rootView = layoutInflater.inflate(R.layout.apod_favorites_list_item, parent, false)
        rootView.setOnClickListener {
            onFavoriteAPoDClicked()
        }
        initializeViews()
        setRemoveFromFavoritesIconOnClickListener()
    }

    private fun initializeViews() {
        imageViewAPoDFavoritesListItemImage =
            findViewById(R.id.image_view_apod_favorites_list_item_image)
        textViewAPoDFavoritesListItemTitle =
            findViewById(R.id.text_view_apod_favorites_list_item_title)
        imageViewRemoveFromFavorites =
            findViewById(R.id.image_view_remove_from_favorites)
    }

    private fun setRemoveFromFavoritesIconOnClickListener() {
        imageViewRemoveFromFavorites.setOnClickListener {
            onRemoveFromFavoritesIconClicked()
        }
    }

    override fun bindFavoriteAPoD(apod: APoD) {
        currentFavoriteAPoD = apod
        GlideProvider.loadResourceFromUrl(apod.url, imageViewAPoDFavoritesListItemImage)
        loadAPoDVideoThumbnail(apod.thumbnailUrl)
        textViewAPoDFavoritesListItemTitle.text = apod.title
    }

    private fun loadAPoDVideoThumbnail(thumbnailUrl: String?) {
        if (thumbnailUrl != null)
            GlideProvider.loadResourceFromUrl(thumbnailUrl, imageViewAPoDFavoritesListItemImage)
    }

    private fun onFavoriteAPoDClicked() {
        listeners.forEach { listener ->
            listener.onFavoriteAPoDClicked(currentFavoriteAPoD)
        }
    }

    private fun onRemoveFromFavoritesIconClicked() {
        listeners.forEach { listener ->
            listener.onRemoveFromFavoritesIconClicked(currentFavoriteAPoD)
        }
    }
}