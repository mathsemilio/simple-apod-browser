package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

class APoDListItemView(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    BaseObservableView<APoDListContract.ListItem.Listener>(),
    APoDListContract.ListItem {

    private var imageViewApodListItemImage: ImageView
    private var textViewApodListItemTitle: TextView

    private lateinit var currentAPoD: APoD

    private var glideProvider: GlideProvider

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_item, parent, false).also { view ->
            view.setOnClickListener { onAPoDClicked() }
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
        listeners.forEach { it.onApodListItemClicked(currentAPoD) }
    }
}