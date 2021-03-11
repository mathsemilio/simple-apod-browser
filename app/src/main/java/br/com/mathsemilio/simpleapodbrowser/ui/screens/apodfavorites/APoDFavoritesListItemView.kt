package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

class APoDFavoritesListItemView(
    private val glideProvider: GlideProvider,
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableView<APoDFavoritesContract.ListItem.Listener>(),
    APoDFavoritesContract.ListItem {

    private lateinit var imageViewApodListItemImageFavorite: ImageView
    private lateinit var textViewApodListItemTitleFavoriteAPoD: TextView
    private lateinit var imageViewRemoveFromFavorites: ImageView
    private lateinit var textViewApodListItemShortExplanationFavorite: TextView

    private lateinit var currentFavoriteAPoD: FavoriteAPoD

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_item_favorite, parent, false)
        rootView.setOnClickListener { onFavoriteAPoDClicked(currentFavoriteAPoD) }
        initializeViews()
        imageViewRemoveFromFavorites.setOnClickListener {
            onRemoveFavoriteAPoDIconClicked(currentFavoriteAPoD)
        }
    }

    private fun initializeViews() {
        imageViewApodListItemImageFavorite =
            findViewById(R.id.image_view_apod_list_item_image_favorite)
        textViewApodListItemTitleFavoriteAPoD =
            findViewById(R.id.text_view_apod_list_item_title_favorite)
        imageViewRemoveFromFavorites =
            findViewById(R.id.image_button_remove_from_favorites)
        textViewApodListItemShortExplanationFavorite =
            findViewById(R.id.text_view_apod_list_item_short_explanation_favorite)
    }

    override fun bindAPoDDetails(favoriteApod: FavoriteAPoD) {
        currentFavoriteAPoD = favoriteApod
        glideProvider.loadResourceFromUrl(favoriteApod.url, imageViewApodListItemImageFavorite)
        textViewApodListItemTitleFavoriteAPoD.text = favoriteApod.title
        textViewApodListItemShortExplanationFavorite.text = favoriteApod.explanation
    }

    private fun onFavoriteAPoDClicked(apod: FavoriteAPoD) {
        listeners.forEach { it.onFavoriteAPoDClicked(apod) }
    }

    private fun onRemoveFavoriteAPoDIconClicked(favoriteApod: FavoriteAPoD) {
        listeners.forEach { it.onRemoveFavoriteAPoDIconClicked(favoriteApod) }
    }
}