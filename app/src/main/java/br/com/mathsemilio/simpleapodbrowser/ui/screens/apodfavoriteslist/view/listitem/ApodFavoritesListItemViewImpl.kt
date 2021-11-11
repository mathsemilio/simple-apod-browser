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
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod

class ApodFavoritesListItemViewImpl(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : ApodFavoritesListItemView() {

    private lateinit var imageViewApodFavoritesListItemImage: ImageView
    private lateinit var textViewApodFavoritesListItemTitle: TextView
    private lateinit var imageViewRemoveFromFavorites: ImageView

    private lateinit var favoriteApod: Apod

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_item, parent, false)

        rootView.setOnClickListener {
            onFavoriteAPoDClicked()
        }

        initializeViews()

        setRemoveFromFavoritesIconOnClickListener()
    }

    private fun initializeViews() {
        imageViewApodFavoritesListItemImage =
            rootView.findViewById(R.id.image_view_apod_favorites_list_item_image)
        textViewApodFavoritesListItemTitle =
            rootView.findViewById(R.id.text_view_apod_favorites_list_item_title)
        imageViewRemoveFromFavorites =
            rootView.findViewById(R.id.image_view_remove_from_favorites)
    }

    private fun setRemoveFromFavoritesIconOnClickListener() {
        imageViewRemoveFromFavorites.setOnClickListener {
            onRemoveFromFavoritesIconClicked()
        }
    }

    override fun bindFavoriteApod(favoriteApod: Apod) {
        this.favoriteApod = favoriteApod

        GlideProvider.loadResourceFromUrl(favoriteApod.url, imageViewApodFavoritesListItemImage)

        loadApodVideoThumbnail()

        textViewApodFavoritesListItemTitle.text = favoriteApod.title
    }

    private fun loadApodVideoThumbnail() {
        favoriteApod.thumbnailUrl?.let { thumbnailUrl ->
            GlideProvider.loadResourceFromUrl(thumbnailUrl, imageViewApodFavoritesListItemImage)
        }
    }

    private fun onFavoriteAPoDClicked() {
        notifyListener { listener ->
            listener.onFavoriteApodClicked(favoriteApod)
        }
    }

    private fun onRemoveFromFavoritesIconClicked() {
        notifyListener { listener ->
            listener.onRemoveFromFavoritesIconClicked(favoriteApod)
        }
    }
}