package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist

import androidx.recyclerview.widget.DiffUtil
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

class APoDFavoritesScreenDiffUtilCallback : DiffUtil.ItemCallback<APoD>() {
    override fun areItemsTheSame(oldItem: APoD, newItem: APoD): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: APoD, newItem: APoD): Boolean {
        return oldItem == newItem
    }
}