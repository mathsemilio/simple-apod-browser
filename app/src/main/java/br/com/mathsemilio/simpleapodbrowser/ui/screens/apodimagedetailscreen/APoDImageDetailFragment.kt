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
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.TapGestureHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.StatusBarManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.SystemUIManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator

class APoDImageDetailFragment : BaseFragment(),
    APoDImageDetailView.Listener,
    TapGestureHelper.Listener {

    private lateinit var view: APoDImageDetailView

    private lateinit var tapGestureHelper: TapGestureHelper
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var statusBarManager: StatusBarManager
    private lateinit var systemUIManager: SystemUIManager

    private lateinit var currentAPoDImage: Bitmap

    private var screenTapped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tapGestureHelper = compositionRoot.tapGestureHelper
        screensNavigator = compositionRoot.screensNavigator
        statusBarManager = compositionRoot.statusBarManager
        systemUIManager = compositionRoot.systemUIManager
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

    override fun onScreenTapped() {
        screenTapped = when (screenTapped) {
            true -> {
                systemUIManager.onShowSystemUI()
                view.showToolbar()
                false
            }
            false -> {
                systemUIManager.onHideSystemUI()
                view.hideToolbar()
                true
            }
        }
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
        tapGestureHelper.addListener(this)
        statusBarManager.onSetStatusBarColor(Color.BLACK)
        bindAPoDImage()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        tapGestureHelper.removeListener(this)
        statusBarManager.onRevertStatusBarColor()
        systemUIManager.onShowSystemUI()
        super.onStop()
    }
}