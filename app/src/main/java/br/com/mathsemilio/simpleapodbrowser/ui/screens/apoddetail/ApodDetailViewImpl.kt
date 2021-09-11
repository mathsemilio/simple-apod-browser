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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_IMAGE
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_VIDEO
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.common.util.formatDate
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod

class ApodDetailViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : ApodDetailView() {

    private lateinit var imageViewApodDetailImage: ImageView
    private lateinit var imageViewPlayIcon: ImageView
    private lateinit var textViewApodDetailTitle: TextView
    private lateinit var textViewApodDetailDate: TextView
    private lateinit var textViewApodDetailExplanation: TextView

    private lateinit var apod: Apod

    init {
        rootView = layoutInflater.inflate(R.layout.apod_detail_screen, container, false)

        initializeViews()

        attachApodImageViewClickListener()
    }

    private fun initializeViews() {
        imageViewApodDetailImage = rootView.findViewById(R.id.image_view_apod_detail_image)
        imageViewPlayIcon = rootView.findViewById(R.id.image_view_play_icon)
        textViewApodDetailTitle = rootView.findViewById(R.id.text_view_apod_detail_title)
        textViewApodDetailDate = rootView.findViewById(R.id.text_view_apod_detail_date)
        textViewApodDetailExplanation = rootView.findViewById(R.id.text_view_apod_detail_explanation)
    }

    private fun attachApodImageViewClickListener() {
        imageViewApodDetailImage.setOnClickListener {
            notifyListener { listener ->
                listener.onApodImageClicked(imageViewApodDetailImage.drawable.toBitmap())
            }
        }
    }

    override fun bindApod(apod: Apod) {
        this.apod = apod
        loadResourcesBasedOnMediaType()

        textViewApodDetailTitle.text = apod.title
        textViewApodDetailDate.text = apod.date.formatDate(context)
        textViewApodDetailExplanation.text = apod.explanation
    }

    private fun loadResourcesBasedOnMediaType() {
        when (apod.mediaType) {
            APOD_TYPE_IMAGE -> loadApodImage()
            APOD_TYPE_VIDEO -> loadApodVideoThumbnail()
        }
    }

    private fun loadApodImage() {
        GlideProvider.loadResourceFromUrl(apod.url, imageViewApodDetailImage)
    }

    private fun loadApodVideoThumbnail() {
        imageViewPlayIcon.apply {
            visibility = View.VISIBLE
            setOnClickListener { onPlayIconClicked(apod.url) }
        }

        apod.thumbnailUrl?.let { url ->
            GlideProvider.loadResourceFromUrl(url, imageViewApodDetailImage)
        }
    }

    private fun onPlayIconClicked(videoUrl: String) {
        notifyListener { listener ->
            listener.onPlayIconClicked(videoUrl)
        }
    }
}