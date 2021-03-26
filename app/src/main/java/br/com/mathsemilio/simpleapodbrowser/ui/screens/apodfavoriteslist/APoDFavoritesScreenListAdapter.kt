package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.APoDFavoritesListItemView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.APoDFavoritesListItemViewImpl

class APoDFavoritesScreenListAdapter(
    private val viewFactory: ViewFactory,
    private val listener: Listener
) : ListAdapter<APoD, APoDFavoritesScreenListAdapter.ViewHolder>(APoDFavoritesScreenDiffUtilCallback()),
    APoDFavoritesListItemView.Listener {

    interface Listener {
        fun onFavoriteAPoDClicked(apod: APoD)
        fun onRemoveFromFavoritesIconClicked(apod: APoD)
    }

    class ViewHolder(private val listItemView: APoDFavoritesListItemViewImpl) :
        RecyclerView.ViewHolder(listItemView.rootView) {

        fun bind(apod: APoD) = listItemView.bindFavoriteAPoD(apod)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(viewFactory.getAPoDFavoritesListItemView(parent).also { listItemView ->
            listItemView.addListener(this)
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onFavoriteAPoDClicked(apod: APoD) {
        listener.onFavoriteAPoDClicked(apod)
    }

    override fun onRemoveFromFavoritesIconClicked(apod: APoD) {
        listener.onRemoveFromFavoritesIconClicked(apod)
    }
}