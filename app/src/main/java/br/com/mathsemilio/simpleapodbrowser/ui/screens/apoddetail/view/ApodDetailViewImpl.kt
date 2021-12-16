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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_IMAGE
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_VIDEO
import br.com.mathsemilio.simpleapodbrowser.common.util.formatDate
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.ImageResourceManager

class ApodDetailViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : ApodDetailView() {

    private lateinit var imageViewApod: ImageView
    private lateinit var imageViewPlayIcon: ImageView

    private lateinit var textViewApodTitle: TextView
    private lateinit var textViewApodDate: TextView
    private lateinit var textViewApodExplanation: TextView

    private lateinit var apod: Apod

    init {
        rootView = layoutInflater.inflate(R.layout.apod_detail_screen, container, false)

        initializeViews()

        attachApodImageViewClickListener()
    }

    private fun initializeViews() {
        imageViewApod = rootView.findViewById(R.id.image_view_apod)
        imageViewPlayIcon = rootView.findViewById(R.id.image_view_play_icon)

        textViewApodTitle = rootView.findViewById(R.id.text_view_apod_title)
        textViewApodDate = rootView.findViewById(R.id.text_view_apod_date)
        textViewApodExplanation = rootView.findViewById(R.id.text_view_apod_explanation)
    }

    private fun attachApodImageViewClickListener() {
        imageViewApod.setOnClickListener {
            notify { listener ->
                listener.onApodImageClicked(imageViewApod.drawable.toBitmap())
            }
        }
    }

    override fun bind(apod: Apod) {
        this.apod = apod
        loadResourcesBasedOnMediaType()

        textViewApodTitle.text = apod.title
        textViewApodDate.text = apod.date.formatDate(context)
        textViewApodExplanation.text = apod.explanation
    }

    private fun loadResourcesBasedOnMediaType() {
        when (apod.mediaType) {
            APOD_TYPE_IMAGE -> loadApodImage()
            APOD_TYPE_VIDEO -> loadApodVideoThumbnail()
        }
    }

    private fun loadApodImage() {
        ImageResourceManager.loadWithPlaceholderFrom(apod.url, imageViewApod)
    }

    private fun loadApodVideoThumbnail() {
        imageViewPlayIcon.apply {
            visibility = View.VISIBLE
            setOnClickListener { onPlayIconClicked(apod.url) }
        }

        apod.thumbnailUrl?.let { url ->
            ImageResourceManager.loadWithPlaceholderFrom(url, imageViewApod)
        }
    }

    private fun onPlayIconClicked(videoUrl: String) {
        notify { listener ->
            listener.onPlayIconClicked(videoUrl)
        }
    }
}
