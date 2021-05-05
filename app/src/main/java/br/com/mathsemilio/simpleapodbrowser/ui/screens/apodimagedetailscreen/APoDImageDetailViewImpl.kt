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
package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodimagedetailscreen

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import br.com.mathsemilio.simpleapodbrowser.R
import com.github.chrisbanes.photoview.PhotoView
import com.google.android.material.appbar.MaterialToolbar

class APoDImageDetailViewImpl(layoutInflater: LayoutInflater, container: ViewGroup?) :
    APoDImageDetailView() {

    private var imageViewAPoDImageDetail: PhotoView
    private var toolbarAPoDImageDetail: MaterialToolbar

    init {
        rootView = layoutInflater.inflate(R.layout.apod_image_detail_screen, container, false)
        imageViewAPoDImageDetail = findViewById(R.id.photo_view_apod_image_detail)
        toolbarAPoDImageDetail = findViewById(R.id.material_toolbar_apod_image_detail)
        setToolbarNavigationIconOnClickListener()
        setToolbarOnMenuItemSelectedListener()
    }

    private fun setToolbarNavigationIconOnClickListener() {
        toolbarAPoDImageDetail.setNavigationOnClickListener {
            listeners.forEach { listener ->
                listener.onToolbarNavigationIconClicked()
            }
        }
    }

    private fun setToolbarOnMenuItemSelectedListener() {
        toolbarAPoDImageDetail.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.toolbar_action_download_apod -> {
                    listeners.forEach { listener ->
                        listener.onToolbarActionExportAPoDImageClicked()
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun showToolbar() {
        toolbarAPoDImageDetail.isVisible = true
    }

    override fun hideToolbar() {
        toolbarAPoDImageDetail.isVisible = false
    }

    override fun bindAPoDImage(apodImage: Bitmap) {
        imageViewAPoDImageDetail.setImageBitmap(apodImage)
    }
}