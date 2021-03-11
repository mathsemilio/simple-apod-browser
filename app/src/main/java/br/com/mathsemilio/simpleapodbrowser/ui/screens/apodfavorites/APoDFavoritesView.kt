package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

class APoDFavoritesView(
    private val glideProvider: GlideProvider,
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : BaseObservableView<APoDFavoritesContract.View.Listener>(),
    APoDFavoritesContract.View,
    APoDFavoritesListAdapter.Listener {

    private lateinit var progressBarApodListFavorites: ProgressBar
    private lateinit var textViewNoFavoriteApodFound: TextView
    private lateinit var recyclerViewApodListFavorites: RecyclerView

    private lateinit var apodFavoritesListAdapter: APoDFavoritesListAdapter

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_favorites_screen, container, false)
        initializeViews()
        setupRecyclerView(layoutInflater)
    }

    private fun initializeViews() {
        progressBarApodListFavorites = findViewById(R.id.progress_bar_apod_list_favorites)
        textViewNoFavoriteApodFound = findViewById(R.id.text_view_no_favorite_apod_found)
        recyclerViewApodListFavorites = findViewById(R.id.recycler_view_apod_list_favorites)
    }

    private fun setupRecyclerView(layoutInflater: LayoutInflater) {
        apodFavoritesListAdapter = APoDFavoritesListAdapter(layoutInflater, glideProvider, this)
        recyclerViewApodListFavorites.adapter = apodFavoritesListAdapter
    }

    override fun bindFavoriteApods(favoritesApods: List<FavoriteAPoD>) {
        apodFavoritesListAdapter.submitList(favoritesApods)
        if (favoritesApods.isEmpty()) {
            recyclerViewApodListFavorites.visibility = View.GONE
            textViewNoFavoriteApodFound.visibility = View.VISIBLE
        } else {
            recyclerViewApodListFavorites.visibility = View.VISIBLE
            textViewNoFavoriteApodFound.visibility = View.GONE
        }
    }

    override fun showProgressIndicator() {
        progressBarApodListFavorites.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        progressBarApodListFavorites.visibility = View.GONE
    }

    override fun onFavoriteAPoDClicked(favoriteAPoD: FavoriteAPoD) {
        listeners.forEach { it.onFavoriteAPoDClicked(favoriteAPoD) }
    }

    override fun onRemoveFavoriteAPoDIconClicked(favoriteAPoD: FavoriteAPoD) {
        listeners.forEach { it.onRemoveFavoriteAPoDIconClicked(favoriteAPoD) }
    }
}