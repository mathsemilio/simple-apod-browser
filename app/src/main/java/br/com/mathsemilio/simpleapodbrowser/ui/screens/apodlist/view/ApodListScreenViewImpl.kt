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
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.ApodListAdapter

class ApodListScreenViewImpl(
    inflater: LayoutInflater,
    container: ViewGroup?,
    private val viewFactory: ViewFactory
) : ApodListScreenView(), ApodListAdapter.Listener {

    private lateinit var progressBarApods: ProgressBar
    private lateinit var imageViewNetworkRequestError: ImageView
    private lateinit var textViewSomethingWentWrong: TextView
    private lateinit var textViewNetworkRequestErrorMessage: TextView
    private lateinit var swipeRefreshLayoutApods: SwipeRefreshLayout
    private lateinit var recyclerViewApods: RecyclerView

    private lateinit var apodListScreenListAdapter: ApodListAdapter

    init {
        rootView = inflater.inflate(R.layout.apod_list_screen, container, false)
        initializeViews()
        setupRecyclerView()
        attachOnSwipeRefreshListener()
    }

    private fun initializeViews() {
        progressBarApods =
            findViewById(R.id.progress_bar_apods)
        imageViewNetworkRequestError =
            findViewById(R.id.image_view_network_request_error)
        textViewSomethingWentWrong =
            findViewById(R.id.text_view_something_went_wrong)
        textViewNetworkRequestErrorMessage =
            findViewById(R.id.text_view_network_error_message)
        swipeRefreshLayoutApods =
            findViewById(R.id.swipe_refresh_layout_apods)
        recyclerViewApods =
            findViewById(R.id.recycler_view_apods)
    }

    private fun setupRecyclerView() {
        apodListScreenListAdapter = ApodListAdapter(viewFactory, this)
        recyclerViewApods.apply {
            adapter = apodListScreenListAdapter
            setHasFixedSize(true)
            setItemViewCacheSize(7)
        }
    }

    private fun attachOnSwipeRefreshListener() {
        swipeRefreshLayoutApods.setOnRefreshListener {
            listeners.forEach { listener ->
                listener.onScreenSwipedToRefresh()
            }
        }
    }

    override fun bindApods(apods: List<Apod>) {
        apodListScreenListAdapter.submitList(apods)
    }

    override fun showProgressIndicator() {
        progressBarApods.isVisible = true
        imageViewNetworkRequestError.isVisible = false
        textViewSomethingWentWrong.isVisible = false
        swipeRefreshLayoutApods.isVisible = false
    }

    override fun hideProgressIndicator() {
        progressBarApods.isVisible = false
        swipeRefreshLayoutApods.isVisible = true
    }

    override fun onRefreshCompleted() {
        swipeRefreshLayoutApods.isRefreshing = false
    }

    override fun showNetworkRequestErrorState(errorMessage: String) {
        recyclerViewApods.isVisible = false
        imageViewNetworkRequestError.isVisible = true
        textViewSomethingWentWrong.isVisible = true
        textViewNetworkRequestErrorMessage.apply {
            isVisible = true
            text = errorMessage
        }
    }

    override fun hideNetworkRequestErrorState() {
        recyclerViewApods.isVisible = true
        imageViewNetworkRequestError.isVisible = false
        textViewSomethingWentWrong.isVisible = false
        textViewNetworkRequestErrorMessage.isVisible = false
    }

    override fun onApodClicked(apod: Apod) {
        listeners.forEach { listener ->
            listener.onApodClicked(apod)
        }
    }
}