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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view

import android.view.*
import android.widget.LinearLayout
import androidx.core.view.isVisible
import br.com.mathsemilio.simpleapodbrowser.R
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.ApodListAdapter

class ApodListScreenViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : ApodListScreenView(),
    ApodListAdapter.Listener {

    private lateinit var linearLayoutScreenErrorState: LinearLayout
    private lateinit var swipeRefreshLayoutApods: SwipeRefreshLayout
    private lateinit var recyclerViewApods: RecyclerView

    private lateinit var apodListAdapter: ApodListAdapter

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_screen, container, false)

        initializeViews()

        setupRecyclerView()

        attachOnSwipeRefreshListener()
    }

    private fun initializeViews() {
        linearLayoutScreenErrorState = findViewById(R.id.linear_layout_screen_error_state)
        swipeRefreshLayoutApods = findViewById(R.id.swipe_refresh_layout_apods)
        recyclerViewApods = findViewById(R.id.recycler_view_apods)
    }

    private fun setupRecyclerView() {
        apodListAdapter = ApodListAdapter(this)

        recyclerViewApods.adapter = apodListAdapter
        recyclerViewApods.setHasFixedSize(true)
    }

    private fun attachOnSwipeRefreshListener() {
        swipeRefreshLayoutApods.setOnRefreshListener {
            notify { listener -> listener.onScreenSwipedToRefresh() }
        }
    }

    override fun bind(apods: List<Apod>) {
        apodListAdapter.submitList(apods)

        if (apods.isEmpty())
            showNetworkRequestErrorState()
        else
            hideNetworkRequestErrorState()
    }

    override fun showProgressIndicator() {
        linearLayoutScreenErrorState.isVisible = false
        swipeRefreshLayoutApods.isRefreshing = true
        recyclerViewApods.isVisible = false
    }

    override fun hideProgressIndicator() {
        linearLayoutScreenErrorState.isVisible = false
        swipeRefreshLayoutApods.isRefreshing = false
        recyclerViewApods.isVisible = true
    }

    override fun showNetworkRequestErrorState() {
        linearLayoutScreenErrorState.isVisible = true
        recyclerViewApods.isVisible = false
    }

    override fun hideNetworkRequestErrorState() {
        linearLayoutScreenErrorState.isVisible = false
        recyclerViewApods.isVisible = true
    }

    override fun onApodClicked(apod: Apod) {
        notify { listener -> listener.onApodClicked(apod) }
    }
}
