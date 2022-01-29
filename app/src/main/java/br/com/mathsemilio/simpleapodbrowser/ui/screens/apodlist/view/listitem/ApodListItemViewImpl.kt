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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.listitem

import android.view.*
import android.widget.*
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.*
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.ImageResourceManager

class ApodListItemViewImpl(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : ApodListItemView() {

    private lateinit var imageViewApod: ImageView
    private lateinit var textViewApodTitle: TextView
    private lateinit var textViewApodType: TextView

    private lateinit var apod: Apod

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_item, parent, false)

        initializeViews()

        rootView.setOnClickListener { notify { listener -> listener.onApodClicked(apod) } }
    }

    private fun initializeViews() {
        imageViewApod = findViewById(R.id.image_view_apod)
        textViewApodTitle = findViewById(R.id.text_view_apod_title)
        textViewApodType = findViewById(R.id.text_view_apod_type)
    }

    override fun bind(apod: Apod) {
        this.apod = apod

        loadApodImageBasedOnMediaType()

        textViewApodTitle.text = apod.title

        configureApodTypeFrom(apod.mediaType)
    }

    private fun loadApodImageBasedOnMediaType() {
        when (apod.mediaType) {
            APOD_TYPE_IMAGE -> ImageResourceManager.loadFrom(apod.url, imageViewApod)
            APOD_TYPE_VIDEO -> loadApodVideoThumbnail()
        }
    }

    private fun loadApodVideoThumbnail() {
        ImageResourceManager.loadWithPlaceholderFrom(apod.thumbnailUrl!!, imageViewApod)
    }

    private fun configureApodTypeFrom(mediaType: String) {
        textViewApodType.text = when (mediaType) {
            APOD_TYPE_IMAGE -> getString(R.string.apod_type_image)
            APOD_TYPE_VIDEO -> getString(R.string.apod_type_video)
            else -> throw IllegalArgumentException(UNKNOWN_APOD_TYPE)
        }
    }
}
