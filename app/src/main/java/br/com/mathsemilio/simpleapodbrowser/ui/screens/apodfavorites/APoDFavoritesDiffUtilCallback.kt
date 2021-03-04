package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import androidx.recyclerview.widget.DiffUtil
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD

class APoDFavoritesDiffUtilCallback : DiffUtil.ItemCallback<FavoriteAPoD>() {
    override fun areItemsTheSame(oldItem: FavoriteAPoD, newItem: FavoriteAPoD): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: FavoriteAPoD, newItem: FavoriteAPoD): Boolean {
        TODO("Not yet implemented")
    }
}