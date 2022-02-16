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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.favoriteapods

import android.view.*
import androidx.recyclerview.widget.*
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.screens.favoriteapods.view.listitem.*

class FavoriteApodsListAdapter(
    private val listener: Listener
) : ListAdapter<Apod, FavoriteApodsListAdapter.ViewHolder>(FavoriteApodsDiffUtilCallback()),
    FavoriteApodsListItemView.Listener {

    interface Listener {
        fun onFavoriteApodClicked(favoriteApod: Apod)
    }

    class ViewHolder(
        private val listItemView: FavoriteApodsListItemView
    ) : RecyclerView.ViewHolder(listItemView.rootView) {

        fun bind(apod: Apod) = listItemView.bind(apod)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FavoriteApodsListItemViewImpl(
            LayoutInflater.from(parent.context),
            parent
        ).also { view -> view.addObserver(this) })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onFavoriteApodClicked(favoriteApod: Apod) {
        listener.onFavoriteApodClicked(favoriteApod)
    }
}
