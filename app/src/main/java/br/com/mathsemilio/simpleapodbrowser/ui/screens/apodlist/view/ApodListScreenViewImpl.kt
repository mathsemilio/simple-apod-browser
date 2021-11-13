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

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.ApodListAdapter

class ApodListScreenViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : ApodListScreenView(), ApodListAdapter.Listener {

    private lateinit var progressBarApods: ProgressBar
    private lateinit var linearLayoutScreenErrorState: LinearLayout
    private lateinit var swipeRefreshLayoutApods: SwipeRefreshLayout
    private lateinit var recyclerViewApods: RecyclerView

    private lateinit var apodListScreenListAdapter: ApodListAdapter

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_screen, container, false)

        initializeViews()

        setupRecyclerView()

        attachOnSwipeRefreshListener()
    }

    private fun initializeViews() {
        progressBarApods = rootView.findViewById(R.id.progress_bar_apod_list)
        linearLayoutScreenErrorState = rootView.findViewById(R.id.linear_layout_screen_error_state)
        swipeRefreshLayoutApods = rootView.findViewById(R.id.swipe_refresh_layout_apod_list)
        recyclerViewApods = rootView.findViewById(R.id.recycler_view_apods)
    }

    private fun setupRecyclerView() {
        apodListScreenListAdapter = ApodListAdapter(this)

        recyclerViewApods.apply {
            adapter = apodListScreenListAdapter
            setHasFixedSize(true)
        }
    }

    private fun attachOnSwipeRefreshListener() {
        swipeRefreshLayoutApods.setOnRefreshListener {
            notify { listener ->
                listener.onScreenSwipedToRefresh()
            }
        }
    }

    override fun bind(apods: List<Apod>) {
        apodListScreenListAdapter.submitList(apods)
    }

    override fun showProgressIndicator() {
        progressBarApods.isVisible = true
        linearLayoutScreenErrorState.isVisible = false
        swipeRefreshLayoutApods.isVisible = false
    }

    override fun hideProgressIndicator() {
        progressBarApods.isVisible = false
        swipeRefreshLayoutApods.isVisible = true
    }

    override fun onRefreshCompleted() {
        swipeRefreshLayoutApods.isRefreshing = false
    }

    override fun showNetworkRequestErrorState() {
        recyclerViewApods.isVisible = false
        linearLayoutScreenErrorState.isVisible = true
    }

    override fun hideNetworkRequestErrorState() {
        recyclerViewApods.isVisible = true
        linearLayoutScreenErrorState.isVisible = false
    }

    override fun onApodClicked(apod: Apod) {
        notify { listener ->
            listener.onApodClicked(apod)
        }
    }
}