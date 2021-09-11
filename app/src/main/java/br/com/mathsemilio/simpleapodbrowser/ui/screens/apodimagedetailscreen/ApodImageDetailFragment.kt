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
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD_IMAGE
import br.com.mathsemilio.simpleapodbrowser.common.OUT_STATE_APOD_IMAGE
import br.com.mathsemilio.simpleapodbrowser.common.WRITE_EXTERNAL_STORAGE_REQUEST_CODE
import br.com.mathsemilio.simpleapodbrowser.common.util.toBitmap
import br.com.mathsemilio.simpleapodbrowser.common.util.toByteArray
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.StatusBarDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.SystemUIDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ApodImageExporter
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.PermissionsHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.TapGestureHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator

class ApodImageDetailFragment : BaseFragment(),
    ApodImageDetailView.Listener,
    PermissionsHelper.Listener,
    ApodImageExporter.Listener,
    TapGestureHelper.Listener {

    private lateinit var view: ApodImageDetailView

    private lateinit var permissionsHelper: PermissionsHelper
    private lateinit var apodImageExporter: ApodImageExporter
    private lateinit var tapGestureHelper: TapGestureHelper
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var statusBarDelegate: StatusBarDelegate
    private lateinit var systemUIDelegate: SystemUIDelegate
    private lateinit var messagesManager: MessagesManager
    private lateinit var dialogManager: DialogManager

    private lateinit var apodImage: Bitmap

    private var screenHasBeenTapped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionsHelper = compositionRoot.permissionsHelper
        apodImageExporter = compositionRoot.apodImageExporter
        tapGestureHelper = compositionRoot.tapGestureHelper
        screensNavigator = compositionRoot.screensNavigator
        statusBarDelegate = compositionRoot.statusBarManager
        systemUIDelegate = compositionRoot.systemUIManager
        messagesManager = compositionRoot.messagesManager
        dialogManager = compositionRoot.dialogManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = ApodImageDetailViewImpl(inflater, container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apodImage = if (savedInstanceState != null)
            savedInstanceState.getByteArray(OUT_STATE_APOD_IMAGE)?.toBitmap() as Bitmap
        else
            getApodImage()
    }

    private fun getApodImage(): Bitmap {
        return requireArguments().getByteArray(ARG_APOD_IMAGE)?.toBitmap() as Bitmap
    }

    override fun onToolbarNavigationIconClicked() {
        screensNavigator.navigateUp()
    }

    override fun onToolbarActionExportApodImageClicked() {
        if (permissionsHelper.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            apodImageExporter.export(apodImage)
        else
            permissionsHelper.requestPermission(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_REQUEST_CODE
            )
    }

    override fun onPermissionRequestResult(result: PermissionsHelper.PermissionResult) {
        when (result) {
            PermissionsHelper.PermissionResult.GRANTED ->
                apodImageExporter.export(apodImage)
            PermissionsHelper.PermissionResult.DENIED ->
                dialogManager.showGrantExternalStoragePermissionDialog()
            PermissionsHelper.PermissionResult.DENIED_PERMANENTLY ->
                dialogManager.showManuallyGrantPermissionDialog()
        }
    }

    override fun onApodImageExportedSuccessfully() {
        messagesManager.showApodImageExportedSuccessfullyMessage()
    }

    override fun onExportApodImageFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onScreenTapped() {
        screenHasBeenTapped = when (screenHasBeenTapped) {
            true -> {
                systemUIDelegate.onShowSystemUI()
                view.showToolbar()
                false
            }
            false -> {
                systemUIDelegate.onHideSystemUI()
                view.hideToolbar()
                true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putByteArray(OUT_STATE_APOD_IMAGE, apodImage.toByteArray())
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
        view.bindApodImage(apodImage)
        permissionsHelper.addListener(this)
        tapGestureHelper.addListener(this)
        apodImageExporter.addListener(this)
        statusBarDelegate.onSetStatusBarColor(Color.BLACK)
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
        permissionsHelper.addListener(this)
        tapGestureHelper.removeListener(this)
        apodImageExporter.removeListener(this)
        systemUIDelegate.onShowSystemUI()
        statusBarDelegate.onRevertStatusBarColor()
    }
}