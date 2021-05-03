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
package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.listitem.APoDFavoritesListItemView

class APoDFavoritesScreenListAdapter(
    private val viewFactory: ViewFactory,
    private val listener: Listener
) : ListAdapter<APoD, APoDFavoritesScreenListAdapter.ViewHolder>(APoDFavoritesScreenDiffUtilCallback()),
    APoDFavoritesListItemView.Listener {

    interface Listener {
        fun onFavoriteAPoDClicked(apod: APoD)
        fun onRemoveFromFavoritesIconClicked(apod: APoD)
    }

    class ViewHolder(private val listItemView: APoDFavoritesListItemView) :
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