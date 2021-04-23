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

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.mathsemilio.simpleapodbrowser.common.*
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.APoDImageExporter
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.TapGestureHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.StatusBarManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.SystemUIManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator

class APoDImageDetailFragment : BaseFragment(),
    APoDImageDetailView.Listener,
    TapGestureHelper.Listener,
    APoDImageExporter.Listener {

    private lateinit var view: APoDImageDetailView

    private lateinit var apodImageExporter: APoDImageExporter
    private lateinit var tapGestureHelper: TapGestureHelper
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var statusBarManager: StatusBarManager
    private lateinit var systemUIManager: SystemUIManager
    private lateinit var messagesManager: MessagesManager
    private lateinit var dialogManager: DialogManager

    private lateinit var currentAPoDImage: Bitmap

    private var screenHasBeenTapped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apodImageExporter = compositionRoot.aPoDImageExporter
        tapGestureHelper = compositionRoot.tapGestureHelper
        screensNavigator = compositionRoot.screensNavigator
        statusBarManager = compositionRoot.statusBarManager
        systemUIManager = compositionRoot.systemUIManager
        messagesManager = compositionRoot.messagesManager
        dialogManager = compositionRoot.dialogManager
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

    override fun onToolbarActionDownloadAPoDClicked() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            apodImageExporter.export(currentAPoDImage)
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_REQ_CODE
            )
        }
    }

    override fun onScreenTapped() {
        screenHasBeenTapped = when (screenHasBeenTapped) {
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

    override fun onAPoDImageExportedSuccessfully() {
        messagesManager.showAPoDImageExportedSuccessfully()
    }

    override fun onErrorExportingAPoDImage() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    private fun getAPoDImage(): Bitmap {
        return requireArguments().getByteArray(ARG_APOD_IMAGE)?.toBitmap()!!
    }

    private fun bindAPoDImage() {
        view.bindAPoDImage(currentAPoDImage)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissions.isEmpty() || grantResults.isEmpty())
            throw RuntimeException(EMPTY_PERMISSION_REQUEST)

        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_REQ_CODE -> {
                when {
                    grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                        apodImageExporter.export(currentAPoDImage)
                    }
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(), permissions[0]
                    ) -> {
                        dialogManager.showGrantExternalStoragePermissionDialog()
                    }
                    else -> {
                        dialogManager.showEnablePermissionsManuallyDialog()
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putByteArray(OUT_STATE_APOD_IMAGE, currentAPoDImage.toByteArray())
    }

    override fun onStart() {
        view.addListener(this)
        tapGestureHelper.addListener(this)
        statusBarManager.onSetStatusBarColor(Color.BLACK)
        apodImageExporter.addListener(this)
        bindAPoDImage()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        tapGestureHelper.removeListener(this)
        apodImageExporter.removeListener(this)
        statusBarManager.onRevertStatusBarColor()
        systemUIManager.onShowSystemUI()
        super.onStop()
    }
}