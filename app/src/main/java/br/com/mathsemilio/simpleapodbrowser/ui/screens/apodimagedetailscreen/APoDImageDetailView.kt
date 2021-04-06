package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodimagedetailscreen

import android.graphics.Bitmap
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

abstract class APoDImageDetailView : BaseObservableView<APoDImageDetailView.Listener>() {

    interface Listener {
        fun onToolbarNavigationIconClicked()
    }

    abstract fun bindAPoDImage(apodImage: Bitmap)
}