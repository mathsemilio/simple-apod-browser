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
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.ApodFavoritesListAdapter

class ApodFavoritesScreenViewImpl(
    inflater: LayoutInflater,
    container: ViewGroup?,
    private val viewFactory: ViewFactory
) : ApodFavoritesScreenView(), ApodFavoritesListAdapter.Listener {

    private lateinit var progressBarApodFavorites: ProgressBar
    private lateinit var imageViewNoFavoriteApodFound: ImageView
    private lateinit var textViewNoFavoriteApodFound: TextView
    private lateinit var textViewClickOnTheAddToFavoritesIcon: TextView
    private lateinit var recyclerViewApodFavorites: RecyclerView

    private lateinit var apodFavoritesListAdapter: ApodFavoritesListAdapter

    private lateinit var lastSwipedFavoriteApod: Apod

    init {
        rootView = inflater.inflate(R.layout.apod_favorites_list_screen, container, false)
        initializeViews()
        setupRecyclerView()
    }

    private fun initializeViews() {
        progressBarApodFavorites =
            findViewById(R.id.progress_bar_apod_favorites)
        imageViewNoFavoriteApodFound =
            findViewById(R.id.image_view_no_favorite_apods_found_illustration)
        textViewNoFavoriteApodFound =
            findViewById(R.id.text_view_no_favorite_apod_found)
        textViewClickOnTheAddToFavoritesIcon =
            findViewById(R.id.text_view_click_on_the_add_to_favorites_icon)
        recyclerViewApodFavorites =
            findViewById(R.id.recycler_view_apod_favorites)
    }

    private fun setupRecyclerView() {
        apodFavoritesListAdapter = ApodFavoritesListAdapter(viewFactory, this)

        ItemTouchHelper(itemTouchHelperCallback)
            .attachToRecyclerView(recyclerViewApodFavorites)

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
                    val currentApodFavoritesList =
                        apodFavoritesListAdapter.currentList.toMutableList()

                    lastSwipedFavoriteApod =
                        currentApodFavoritesList.removeAt(viewHolder.adapterPosition)

                    apodFavoritesListAdapter.submitList(currentApodFavoritesList)

                    notifyFavoriteApodSwipedToDelete()
                }
            }
        }

    override fun bindFavoriteApods(favoriteApods: List<Apod>) {
        apodFavoritesListAdapter.submitList(favoriteApods)
        if (favoriteApods.isEmpty())
            showEmptyFavoriteApodsScreenState()
        else
            showFavoriteApodsScreenState()
    }

    private fun showEmptyFavoriteApodsScreenState() {
        imageViewNoFavoriteApodFound.isVisible = true
        textViewNoFavoriteApodFound.isVisible = true
        textViewClickOnTheAddToFavoritesIcon.isVisible = true
        recyclerViewApodFavorites.isVisible = false
    }

    private fun showFavoriteApodsScreenState() {
        imageViewNoFavoriteApodFound.isVisible = false
        textViewNoFavoriteApodFound.isVisible = false
        textViewClickOnTheAddToFavoritesIcon.isVisible = false
        recyclerViewApodFavorites.isVisible = true
    }

    override fun showProgressIndicator() {
        progressBarApodFavorites.isVisible = true
    }

    override fun hideProgressIndicator() {
        progressBarApodFavorites.isVisible = false
    }

    override fun onFavoriteApodClicked(favoriteApod: Apod) {
        listeners.forEach { listener ->
            listener.onFavoriteApodClicked(favoriteApod)
        }
    }

    override fun onRemoveFromFavoritesIconClicked(apod: Apod) {
        listeners.forEach { listener ->
            listener.onRemoveFromFavoritesIconClicked(apod)
        }
    }

    private fun notifyFavoriteApodSwipedToDelete() {
        listeners.forEach { listener ->
            listener.onFavoriteApodSwipedToDelete(lastSwipedFavoriteApod)
        }
    }
}