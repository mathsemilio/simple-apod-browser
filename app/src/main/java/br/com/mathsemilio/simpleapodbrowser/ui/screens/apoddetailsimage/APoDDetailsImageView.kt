package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsimage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.formatAPoDDate
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseView

class APoDDetailsImageView(
    private val glideProvider: GlideProvider,
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : BaseView(), APoDDetailsImageContract.View {

    private lateinit var imageViewApodDetailImage: ImageView
    private lateinit var textViewApodDetailWithImageTitle: TextView
    private lateinit var textViewApodDetailWithImageDate: TextView
    private lateinit var textViewApodDetailWithImageExplanation: TextView

    init {
        rootView = layoutInflater.inflate(R.layout.apod_detail_with_image, container, false)
        initializeViews()
    }

    private fun initializeViews() {
        imageViewApodDetailImage =
            findViewById(R.id.image_view_apod_detail_image)
        textViewApodDetailWithImageTitle =
            findViewById(R.id.text_view_apod_detail_with_image_title)
        textViewApodDetailWithImageDate =
            findViewById(R.id.text_view_apod_detail_with_image_date)
        textViewApodDetailWithImageExplanation =
            findViewById(R.id.text_view_apod_detail_with_image_explanation)
    }

    override fun bindAPoDDetails(apod: APoD) {
        glideProvider.loadResourceFromUrl(apod.url, imageViewApodDetailImage)
        textViewApodDetailWithImageTitle.text = apod.title
        textViewApodDetailWithImageDate.text = apod.date.formatAPoDDate(context)
        textViewApodDetailWithImageExplanation.text = apod.explanation
    }
}