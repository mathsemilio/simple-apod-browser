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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.ApodFavoritesListAdapter

class ApodFavoritesScreenViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : ApodFavoritesScreenView(), ApodFavoritesListAdapter.Listener {

    private lateinit var progressBarApodFavorites: ProgressBar
    private lateinit var linearLayoutScreenEmptyState: LinearLayout
    private lateinit var recyclerViewApodFavorites: RecyclerView

    private lateinit var apodFavoritesListAdapter: ApodFavoritesListAdapter

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
        apodFavoritesListAdapter = ApodFavoritesListAdapter(this)

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewApodFavorites)

        recyclerViewApodFavorites.apply {
            adapter = apodFavoritesListAdapter
            setHasFixedSize(true)
        }
    }

    private val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback
        get() {
            return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val currentApodFavoritesList = apodFavoritesListAdapter.currentList.toMutableList()

                    lastSwipedFavoriteApod = currentApodFavoritesList.removeAt(viewHolder.adapterPosition)

                    apodFavoritesListAdapter.submitList(currentApodFavoritesList)

                    notifyFavoriteApodSwipedToDelete()
                }
            }
        }

    override fun bindFavoriteApods(favoriteApods: List<Apod>) {
        apodFavoritesListAdapter.submitList(favoriteApods)

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
        notifyListener { listener ->
            listener.onFavoriteApodClicked(favoriteApod)
        }
    }

    private fun notifyFavoriteApodSwipedToDelete() {
        notifyListener { listener ->
            listener.onFavoriteApodSwipedToDelete(lastSwipedFavoriteApod)
        }
    }
}