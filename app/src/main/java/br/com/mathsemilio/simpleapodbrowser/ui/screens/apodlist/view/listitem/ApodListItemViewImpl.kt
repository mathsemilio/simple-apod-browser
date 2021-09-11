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

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod

class ApodListItemViewImpl(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : ApodListItemView() {

    private var imageViewApodListItemImage: ImageView
    private var textViewApodListItemTitle: TextView

    private lateinit var apod: Apod

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_item, parent, false)

        rootView.setOnClickListener {
            onApodClicked()
        }

        imageViewApodListItemImage = rootView.findViewById(R.id.image_view_apod_list_item_image)
        textViewApodListItemTitle = rootView.findViewById(R.id.text_view_apod_list_item_title)
    }

    private fun onApodClicked() {
        notifyListener { listener ->
            listener.onApodClicked(apod)
        }
    }

    override fun bindApodDetails(apod: Apod) {
        this.apod = apod

        GlideProvider.loadResourceFromUrl(apod.url, imageViewApodListItemImage)

        loadApodVideoThumbnail()

        textViewApodListItemTitle.text = apod.title
    }

    private fun loadApodVideoThumbnail() {
        apod.thumbnailUrl?.let { thumbnailUrl ->
            GlideProvider.loadResourceFromUrl(thumbnailUrl, imageViewApodListItemImage)
        }
    }
}