package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

class APoDListItemViewImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    APoDListItemView() {

    private var imageViewApodListItemImage: ImageView
    private var textViewApodListItemTitle: TextView

    private lateinit var currentAPoD: APoD

    private var glideProvider: GlideProvider

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_item, parent, false)
        rootView.setOnClickListener {
            onAPoDClicked()
        }
        imageViewApodListItemImage = findViewById(R.id.image_view_apod_list_item_image)
        textViewApodListItemTitle = findViewById(R.id.text_view_apod_list_item_title)
        glideProvider = GlideProvider(context)
    }

    override fun bindAPoDDetails(apod: APoD) {
        currentAPoD = apod
        glideProvider.loadResourceAsThumbnail(apod.url, imageViewApodListItemImage)
        loadAPoDVideoThumbnail(apod.thumbnailUrl)
        textViewApodListItemTitle.text = apod.title
    }

    private fun loadAPoDVideoThumbnail(thumbnailUrl: String?) {
        if (thumbnailUrl != null)
            glideProvider.loadResourceAsThumbnail(thumbnailUrl, imageViewApodListItemImage)
    }

    private fun onAPoDClicked() {
        listeners.forEach { listener ->
            listener.onAPoDClicked(currentAPoD)
        }
    }
}