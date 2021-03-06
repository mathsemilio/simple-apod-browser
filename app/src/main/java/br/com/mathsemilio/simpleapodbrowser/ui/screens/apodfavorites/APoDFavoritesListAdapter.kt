package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD

class APoDFavoritesListAdapter(
    private val layoutInflater: LayoutInflater,
    private val listener: Listener
) : ListAdapter<FavoriteAPoD, APoDFavoritesListAdapter.ViewHolder>(APoDFavoritesDiffUtilCallback()),
    APoDFavoritesContract.ListItem.Listener {

    interface Listener {
        fun onFavoriteAPoDClicked(favoriteAPoD: FavoriteAPoD)
        fun onRemoveFavoriteAPoDIconClicked(favoriteAPoD: FavoriteAPoD)
    }

    class ViewHolder(listItemView: APoDFavoritesListItemView) :
        RecyclerView.ViewHolder(listItemView.rootView) {
        val apodFavoritesListItemView = listItemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val apodFavoritesListItem = APoDFavoritesListItemView(layoutInflater, parent)
        apodFavoritesListItem.addListener(this)
        return ViewHolder(apodFavoritesListItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apodFavoritesListItemView.bindAPoDDetails(getItem(position))
    }

    override fun onFavoriteAPoDClicked(favoriteApod: FavoriteAPoD) {
        listener.onFavoriteAPoDClicked(favoriteApod)
    }

    override fun onRemoveFavoriteAPoDIconClicked(favoriteApod: FavoriteAPoD) {
        listener.onRemoveFavoriteAPoDIconClicked(favoriteApod)
    }
}