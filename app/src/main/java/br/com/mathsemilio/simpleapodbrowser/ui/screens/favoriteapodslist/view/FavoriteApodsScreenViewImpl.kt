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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.favoriteapodslist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.util.onRecyclerViewItemSwipedToTheLeft
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.screens.favoriteapodslist.FavoriteApodsListAdapter

class FavoriteApodsScreenViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : FavoriteApodsScreenView(),
    FavoriteApodsListAdapter.Listener {

    private lateinit var progressBarApodFavorites: ProgressBar
    private lateinit var linearLayoutScreenEmptyState: LinearLayout
    private lateinit var recyclerViewApodFavorites: RecyclerView

    private lateinit var favoriteApodsListAdapter: FavoriteApodsListAdapter

    private lateinit var lastSwipedFavoriteApod: Apod

    init {
        rootView = layoutInflater.inflate(R.layout.apod_favorites_list_screen, container, false)

        initializeViews()

        setupRecyclerView()
    }

    private fun initializeViews() {
        progressBarApodFavorites = rootView.findViewById(R.id.progress_bar_apod_favorites)
        linearLayoutScreenEmptyState = rootView.findViewById(R.id.linear_layout_screen_empty_state)
        recyclerViewApodFavorites = rootView.findViewById(R.id.recycler_view_apod_favorites)
    }

    private fun setupRecyclerView() {
        favoriteApodsListAdapter = FavoriteApodsListAdapter(this)

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewApodFavorites)

        recyclerViewApodFavorites.apply {
            adapter = favoriteApodsListAdapter
            setHasFixedSize(true)
        }
    }

    private val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback
        get() = onRecyclerViewItemSwipedToTheLeft { viewHolder ->
            val apodFavoritesList = favoriteApodsListAdapter.currentList.toMutableList()

            lastSwipedFavoriteApod = apodFavoritesList.removeAt(viewHolder.adapterPosition)

            favoriteApodsListAdapter.submitList(apodFavoritesList)

            notify { listener ->
                listener.onFavoriteApodSwipedToDelete(lastSwipedFavoriteApod)
            }
        }

    override fun bind(favoriteApods: List<Apod>) {
        favoriteApodsListAdapter.submitList(favoriteApods)

        if (favoriteApods.isEmpty())
            showScreenEmptyState()
        else
            hideScreenEmptyState()
    }

    private fun showScreenEmptyState() {
        recyclerViewApodFavorites.isVisible = false
        linearLayoutScreenEmptyState.isVisible = true
    }

    private fun hideScreenEmptyState() {
        recyclerViewApodFavorites.isVisible = true
        linearLayoutScreenEmptyState.isVisible = false
    }

    override fun showProgressIndicator() {
        progressBarApodFavorites.isVisible = true
    }

    override fun hideProgressIndicator() {
        progressBarApodFavorites.isVisible = false
    }

    override fun onFavoriteApodClicked(favoriteApod: Apod) {
        notify { listener ->
            listener.onFavoriteApodClicked(favoriteApod)
        }
    }
}
