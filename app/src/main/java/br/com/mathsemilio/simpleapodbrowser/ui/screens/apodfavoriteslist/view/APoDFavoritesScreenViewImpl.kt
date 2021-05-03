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
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.APoDFavoritesScreenListAdapter

class APoDFavoritesScreenViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?,
    private val viewFactory: ViewFactory
) : APoDFavoritesScreenView(),
    APoDFavoritesScreenListAdapter.Listener {

    private var progressBarAPoDFavoritesList: ProgressBar
    private var textViewNoFavoriteAPodFound: TextView
    private var recyclerViewAPoDFavoritesList: RecyclerView

    private lateinit var aPoDFavoritesScreenListAdapter: APoDFavoritesScreenListAdapter

    init {
        rootView = layoutInflater.inflate(R.layout.apod_favorites_list_screen, container, false)
        progressBarAPoDFavoritesList = findViewById(R.id.progress_bar_apod_favorites_list)
        textViewNoFavoriteAPodFound = findViewById(R.id.text_view_no_favorite_apod_found)
        recyclerViewAPoDFavoritesList = findViewById(R.id.recycler_view_apod_favorites_list)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        aPoDFavoritesScreenListAdapter = APoDFavoritesScreenListAdapter(viewFactory, this)
        recyclerViewAPoDFavoritesList.apply {
            adapter = aPoDFavoritesScreenListAdapter
            setHasFixedSize(true)
        }
    }

    override fun bindFavoriteApods(favoriteApods: List<APoD>) {
        aPoDFavoritesScreenListAdapter.submitList(favoriteApods)
        if (favoriteApods.isEmpty()) {
            textViewNoFavoriteAPodFound.visibility = View.VISIBLE
            recyclerViewAPoDFavoritesList.visibility = View.INVISIBLE
        } else {
            textViewNoFavoriteAPodFound.visibility = View.GONE
            recyclerViewAPoDFavoritesList.visibility = View.VISIBLE
        }
    }

    override fun showProgressIndicator() {
        progressBarAPoDFavoritesList.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        progressBarAPoDFavoritesList.visibility = View.GONE
    }

    override fun onFavoriteAPoDClicked(apod: APoD) {
        listeners.forEach { listener ->
            listener.onFavoriteAPoDClicked(apod)
        }
    }

    override fun onRemoveFromFavoritesIconClicked(apod: APoD) {
        listeners.forEach { listener ->
            listener.onRemoveFromFavoritesIconClicked(apod)
        }
    }
}