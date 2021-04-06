package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodimagedetailscreen

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD_IMAGE
import br.com.mathsemilio.simpleapodbrowser.common.OUT_STATE_APOD_IMAGE
import br.com.mathsemilio.simpleapodbrowser.common.toBitmap
import br.com.mathsemilio.simpleapodbrowser.common.toByteArray
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.StatusBarManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator

class APoDImageDetailFragment : BaseFragment(), APoDImageDetailView.Listener {

    private lateinit var view: APoDImageDetailView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var statusBarManager: StatusBarManager

    private lateinit var currentAPoDImage: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screensNavigator = compositionRoot.screensNavigator
        statusBarManager = compositionRoot.statusBarManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getAPoDImageDetailView(container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentAPoDImage = if (savedInstanceState != null)
            savedInstanceState.getByteArray(OUT_STATE_APOD_IMAGE)?.toBitmap()!!
        else
            getAPoDImage()
    }

    override fun onToolbarNavigationIconClicked() {
        screensNavigator.navigateUp()
    }

    private fun getAPoDImage(): Bitmap {
        return requireArguments().getByteArray(ARG_APOD_IMAGE)?.toBitmap()!!
    }

    private fun bindAPoDImage() {
        view.bindAPoDImage(currentAPoDImage)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putByteArray(OUT_STATE_APOD_IMAGE, currentAPoDImage.toByteArray())
    }

    override fun onStart() {
        view.addListener(this)
        statusBarManager.setStatusBarColor(Color.BLACK)
        bindAPoDImage()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        statusBarManager.revertStatusBarColor()
        super.onStop()
    }
}