package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view

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

    private var glideProvider: GlideProvider

    init {
        rootView = layoutInflater.inflate(R.layout.apod_favorites_list_item, parent, false)
        rootView.setOnClickListener {
            onFavoriteAPoDClicked()
        }
        initializeViews()
        setRemoveFromFavoritesIconOnClickListener()
        glideProvider = GlideProvider(context)
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
        glideProvider.loadResourceFromUrl(apod.url, imageViewAPoDFavoritesListItemImage)
        loadAPoDVideoThumbnail(apod.thumbnailUrl)
        textViewAPoDFavoritesListItemTitle.text = apod.title
    }

    private fun loadAPoDVideoThumbnail(thumbnailUrl: String?) {
        if (thumbnailUrl != null)
            glideProvider.loadResourceFromUrl(thumbnailUrl, imageViewAPoDFavoritesListItemImage)
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