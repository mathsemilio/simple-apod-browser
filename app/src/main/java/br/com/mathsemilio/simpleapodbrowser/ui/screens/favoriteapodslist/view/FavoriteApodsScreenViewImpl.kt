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

import android.view.*
import android.widget.LinearLayout
import androidx.core.view.isVisible
import br.com.mathsemilio.simpleapodbrowser.R
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.ApodListAdapter

class FavoriteApodsScreenViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : FavoriteApodsScreenView(),
    ApodListAdapter.Listener {

    private lateinit var linearLayoutScreenEmptyState: LinearLayout
    private lateinit var swipeRefreshLayoutFavoriteApods: SwipeRefreshLayout
    private lateinit var recyclerViewApodFavorites: RecyclerView

    private lateinit var apodListAdapter: ApodListAdapter

    init {
        rootView = layoutInflater.inflate(R.layout.apod_favorites_list_screen, container, false)

        initializeViews()

        setupRecyclerView()

        swipeRefreshLayoutFavoriteApods.isEnabled = false
    }

    private fun initializeViews() {
        linearLayoutScreenEmptyState = findViewById(R.id.linear_layout_screen_empty_state)
        swipeRefreshLayoutFavoriteApods = findViewById(R.id.swipe_refresh_layout_favorite_apods)
        recyclerViewApodFavorites = findViewById(R.id.recycler_view_favorite_apods)
    }

    private fun setupRecyclerView() {
        apodListAdapter = ApodListAdapter(this)

        recyclerViewApodFavorites.adapter = apodListAdapter
        recyclerViewApodFavorites.setHasFixedSize(true)
    }

    override fun bind(favoriteApods: List<Apod>) {
        apodListAdapter.submitList(favoriteApods)

        if (favoriteApods.isEmpty())
            showScreenEmptyState()
        else
            hideScreenEmptyState()
    }

    private fun showScreenEmptyState() {
        recyclerViewApodFavorites.isVisible = false
        swipeRefreshLayoutFavoriteApods.isVisible = false
        linearLayoutScreenEmptyState.isVisible = true
    }

    private fun hideScreenEmptyState() {
        recyclerViewApodFavorites.isVisible = true
        swipeRefreshLayoutFavoriteApods.isVisible = false
        linearLayoutScreenEmptyState.isVisible = false
    }

    override fun showProgressIndicator() {
        swipeRefreshLayoutFavoriteApods.isVisible = true
        recyclerViewApodFavorites.isVisible = false
    }

    override fun hideProgressIndicator() {
        swipeRefreshLayoutFavoriteApods.isVisible = false
        recyclerViewApodFavorites.isVisible = true
    }

    override fun onApodClicked(apod: Apod) {
        notify { listener -> listener.onFavoriteApodClicked(apod) }
    }
}
