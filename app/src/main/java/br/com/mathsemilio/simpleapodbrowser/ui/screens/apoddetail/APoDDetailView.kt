package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_IMAGE
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_VIDEO
import br.com.mathsemilio.simpleapodbrowser.common.formatAPoDDate
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

class APoDDetailView(
    private val glideProvider: GlideProvider,
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : BaseObservableView<APoDDetailContract.View.Listener>(), APoDDetailContract.View {

    private lateinit var imageViewApodDetailImage: ImageView
    private lateinit var imageViewPlayIcon: ImageView
    private lateinit var textViewApodDetailWithImageTitle: TextView
    private lateinit var textViewApodDetailWithImageDate: TextView
    private lateinit var textViewApodDetailWithImageExplanation: TextView

    init {
        rootView = layoutInflater.inflate(R.layout.apod_detail_screen, container, false)
        initializeViews()
    }

    private fun initializeViews() {
        imageViewApodDetailImage =
            findViewById(R.id.image_view_apod_detail_image)
        imageViewPlayIcon =
            findViewById(R.id.image_view_play_icon)
        textViewApodDetailWithImageTitle =
            findViewById(R.id.text_view_apod_detail_title)
        textViewApodDetailWithImageDate =
            findViewById(R.id.text_view_apod_detail_date)
        textViewApodDetailWithImageExplanation =
            findViewById(R.id.text_view_apod_detail_explanation)
    }

    override fun bindAPoDDetails(apod: APoD) {
        loadResourcesBasedOnMediaType(apod)
        textViewApodDetailWithImageTitle.text = apod.title
        textViewApodDetailWithImageDate.text = apod.date.formatAPoDDate(context)
        textViewApodDetailWithImageExplanation.text = apod.explanation
    }

    private fun loadResourcesBasedOnMediaType(apod: APoD) {
        when (apod.mediaType) {
            APOD_TYPE_IMAGE -> loadAPoDImage(apod.url)
            APOD_TYPE_VIDEO -> loadAPoDVideoThumbnail(apod.url, apod.thumbnailUrl)
        }
    }

    private fun loadAPoDImage(url: String) {
        glideProvider.loadResourceFromUrl(url, imageViewApodDetailImage)
    }

    private fun loadAPoDVideoThumbnail(videoUrl: String, thumbnailUrl: String?) {
        imageViewPlayIcon.apply {
            visibility = View.VISIBLE
            setOnClickListener { onPlayIconClicked(videoUrl) }
        }
        thumbnailUrl?.let { url ->
            glideProvider.loadResourceFromUrl(url, imageViewApodDetailImage)
        }
    }

    private fun onPlayIconClicked(videoUrl: String) {
        listeners.forEach { it.onPlayIconClicked(videoUrl) }
    }
}