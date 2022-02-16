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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.latestapods.view

import android.view.*
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.screens.latestapods.LatestApodsAdapter

class LatestApodsScreenViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : LatestApodsScreenView(),
    LatestApodsAdapter.Listener {

    private lateinit var linearLayoutScreenErrorState: LinearLayout
    private lateinit var swipeRefreshLayoutLatestApods: SwipeRefreshLayout
    private lateinit var recyclerViewLatestApods: RecyclerView

    private lateinit var latestApodsAdapter: LatestApodsAdapter

    init {
        rootView = layoutInflater.inflate(R.layout.latest_apods_screen, container, false)

        initializeViews()

        setupRecyclerView()

        attachOnSwipeRefreshListener()
    }

    private fun initializeViews() {
        linearLayoutScreenErrorState = findViewById(R.id.linear_layout_screen_error_state)
        swipeRefreshLayoutLatestApods = findViewById(R.id.swipe_refresh_layout_latest_apods)
        recyclerViewLatestApods = findViewById(R.id.recycler_view_latest_apods)
    }

    private fun setupRecyclerView() {
        latestApodsAdapter = LatestApodsAdapter(this)

        recyclerViewLatestApods.adapter = latestApodsAdapter
        recyclerViewLatestApods.setHasFixedSize(true)
    }

    private fun attachOnSwipeRefreshListener() {
        swipeRefreshLayoutLatestApods.setOnRefreshListener {
            notify { listener -> listener.onScreenSwipedToRefresh() }
        }
    }

    override fun bind(apods: List<Apod>) {
        latestApodsAdapter.submitList(apods)

        if (apods.isEmpty())
            showNetworkRequestErrorState()
        else
            hideNetworkRequestErrorState()
    }

    override fun showProgressIndicator() {
        linearLayoutScreenErrorState.isVisible = false
        swipeRefreshLayoutLatestApods.isRefreshing = true
        recyclerViewLatestApods.isVisible = false
    }

    override fun hideProgressIndicator() {
        linearLayoutScreenErrorState.isVisible = false
        swipeRefreshLayoutLatestApods.isRefreshing = false
        recyclerViewLatestApods.isVisible = true
    }

    override fun showNetworkRequestErrorState() {
        linearLayoutScreenErrorState.isVisible = true
        recyclerViewLatestApods.isVisible = false
    }

    override fun hideNetworkRequestErrorState() {
        linearLayoutScreenErrorState.isVisible = false
        recyclerViewLatestApods.isVisible = true
    }

    override fun onApodClicked(apod: Apod) {
        notify { listener -> listener.onApodClicked(apod) }
    }
}
