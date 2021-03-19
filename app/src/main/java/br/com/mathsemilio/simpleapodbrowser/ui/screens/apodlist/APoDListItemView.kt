package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

class APoDListItemView(
    private val glideProvider: GlideProvider,
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableView<APoDListContract.ListItem.Listener>(),
    APoDListContract.ListItem {

    private lateinit var imageViewApodListItemImage: ImageView
    private lateinit var textViewApodListItemTitle: TextView

    private lateinit var currentAPoD: APoD

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_item, parent, false)
        rootView.setOnClickListener { onAPoDClicked() }
        initializeViews()
    }

    private fun initializeViews() {
        imageViewApodListItemImage = findViewById(R.id.image_view_apod_list_item_image)
        textViewApodListItemTitle = findViewById(R.id.text_view_apod_list_item_title)
    }

    override fun bindAPoDDetails(apod: APoD) {
        currentAPoD = apod
        glideProvider.loadResourceFromUrl(apod.url, imageViewApodListItemImage)
        textViewApodListItemTitle.text = apod.title
    }

    private fun onAPoDClicked() {
        listeners.forEach { it.onApodListItemClicked(currentAPoD) }
    }
}