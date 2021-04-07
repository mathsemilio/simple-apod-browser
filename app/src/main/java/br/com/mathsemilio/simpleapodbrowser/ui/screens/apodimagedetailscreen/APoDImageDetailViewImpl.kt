package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodimagedetailscreen

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import br.com.mathsemilio.simpleapodbrowser.R
import com.google.android.material.appbar.MaterialToolbar

class APoDImageDetailViewImpl(layoutInflater: LayoutInflater, container: ViewGroup?) :
    APoDImageDetailView() {

    private var imageViewAPoDImageDetail: ImageView
    private var toolbarAPoDImageDetail: MaterialToolbar

    init {
        rootView = layoutInflater.inflate(R.layout.apod_image_detail_screen, container, false)
        imageViewAPoDImageDetail = findViewById(R.id.image_view_apod_image_detail)
        toolbarAPoDImageDetail = findViewById(R.id.material_toolbar_apod_image_detail)
        setToolbarNavigationIconOnClickListener()
    }

    private fun setToolbarNavigationIconOnClickListener() {
        toolbarAPoDImageDetail.setNavigationOnClickListener {
            listeners.forEach { listener ->
                listener.onToolbarNavigationIconClicked()
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