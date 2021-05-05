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
import br.com.mathsemilio.simpleapodbrowser.common.formatDate
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

class APoDDetailViewImpl(layoutInflater: LayoutInflater, container: ViewGroup?) : APoDDetailView() {

    private lateinit var imageViewApodDetailImage: ImageView
    private lateinit var imageViewPlayIcon: ImageView
    private lateinit var textViewApodDetailWithImageTitle: TextView
    private lateinit var textViewApodDetailWithImageDate: TextView
    private lateinit var textViewApodDetailWithImageExplanation: TextView

    init {
        rootView = layoutInflater.inflate(R.layout.apod_detail_screen, container, false)
        initializeViews()
        attachAPoDImageViewOnClickListener()
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

    private fun attachAPoDImageViewOnClickListener() {
        imageViewApodDetailImage.setOnClickListener {
            listeners.forEach { listener ->
                listener.onAPoDImageClicked(imageViewApodDetailImage.drawable.toBitmap())
            }
        }
    }

    override fun bindAPoDDetails(apod: APoD) {
        loadResourcesBasedOnMediaType(apod)
        textViewApodDetailWithImageTitle.text = apod.title
        textViewApodDetailWithImageDate.text = apod.date.formatDate(context)
        textViewApodDetailWithImageExplanation.text = apod.explanation
    }

    private fun loadResourcesBasedOnMediaType(apod: APoD) {
        when (apod.mediaType) {
            APOD_TYPE_IMAGE -> loadAPoDImage(apod.url)
            APOD_TYPE_VIDEO -> loadAPoDVideoThumbnail(apod.url, apod.thumbnailUrl)
        }
    }

    private fun loadAPoDImage(url: String) {
        GlideProvider.loadResourceFromUrl(url, imageViewApodDetailImage)
    }

    private fun loadAPoDVideoThumbnail(videoUrl: String, thumbnailUrl: String?) {
        imageViewPlayIcon.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                onPlayIconClicked(videoUrl)
            }
        }
        thumbnailUrl?.let { url ->
            GlideProvider.loadResourceFromUrl(url, imageViewApodDetailImage)
        }
    }

    private fun onPlayIconClicked(videoUrl: String) {
        listeners.forEach { listener ->
            listener.onPlayIconClicked(videoUrl)
        }
    }
}