package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

class APoDListScreenView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<APoDListContract.View.Listener>(),
    APoDListContract.View,
    APoDListScreenListAdapter.Listener {

    private lateinit var progressBarApodList: ProgressBar
    private lateinit var imageViewApodListNetworkRequestError: ImageView
    private lateinit var textViewApodListHttpError: TextView
    private lateinit var swipeRefreshLayoutApodListScreen: SwipeRefreshLayout
    private lateinit var recyclerViewApodList: RecyclerView

    private lateinit var apodListScreenListAdapter: APoDListScreenListAdapter

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_screen, container, false)
        initializeViews()
        setupRecyclerView(layoutInflater)
        attachOnSwipeRefreshListener()
    }

    private fun initializeViews() {
        progressBarApodList = findViewById(R.id.progress_bar_apod_list)
        imageViewApodListNetworkRequestError = findViewById(R.id.image_view_apod_list_network_request_error)
        textViewApodListHttpError = findViewById(R.id.text_view_apod_list_http_error)
        swipeRefreshLayoutApodListScreen = findViewById(R.id.swipe_refresh_layout_apod_list_screen)
        recyclerViewApodList = findViewById(R.id.recycler_view_apod_list)
    }

    private fun setupRecyclerView(layoutInflater: LayoutInflater) {
        apodListScreenListAdapter = APoDListScreenListAdapter(layoutInflater, this)
        recyclerViewApodList.adapter = apodListScreenListAdapter
    }

    private fun attachOnSwipeRefreshListener() {
        swipeRefreshLayoutApodListScreen.setOnRefreshListener {
            listeners.forEach { it.onScreenSwipedToRefresh() }
        }
    }

    override fun bindApods(apods: List<APoD>) {
        apodListScreenListAdapter.submitList(apods)
    }

    override fun showProgressIndicator() {
        progressBarApodList.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        progressBarApodList.visibility = View.GONE
    }

    override fun onRefreshCompleted() {
        swipeRefreshLayoutApodListScreen.isRefreshing = false
    }

    override fun showNetworkRequestErrorState(errorCode: String) {
        recyclerViewApodList.visibility = View.GONE
        imageViewApodListNetworkRequestError.visibility = View.VISIBLE
        textViewApodListHttpError.apply {
            visibility = View.VISIBLE
            text = context.getString(R.string.something_went_wrong_http_error, errorCode)
        }
    }

    override fun hideNetworkRequestFailedState() {
        recyclerViewApodList.visibility = View.VISIBLE
        imageViewApodListNetworkRequestError.visibility = View.GONE
        textViewApodListHttpError.visibility = View.GONE
    }

    override fun onAPoDClicked(apod: APoD) {
        listeners.forEach { it.onApodClicked(apod) }
    }
}