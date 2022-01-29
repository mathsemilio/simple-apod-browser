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

import android.view.*
import android.Manifest
import android.os.Bundle
import android.graphics.Bitmap
import br.com.mathsemilio.simpleapodbrowser.common.*
import androidx.navigation.fragment.findNavController
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.*
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.*
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.common.util.converter.toBitmap
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.SystemUIDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodimagedetailscreen.view.*

class ApodImageDetailFragment : BaseFragment(),
    ApodImageDetailView.Listener,
    PermissionsHelper.Listener,
    TapGestureHelper.Listener,
    ImageExporter.Listener {

    private lateinit var view: ApodImageDetailView

    private lateinit var permissionsHelper: PermissionsHelper
    private lateinit var imageExporter: ImageExporter

    private lateinit var systemUIDelegate: SystemUIDelegate

    private lateinit var tapGestureHelper: TapGestureHelper

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var dialogManager: DialogManager

    private lateinit var apodImage: Bitmap

    private var screenHasBeenTapped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionsHelper = compositionRoot.permissionsHelper
        imageExporter = compositionRoot.imageExporter

        systemUIDelegate = compositionRoot.systemUIDelegate

        tapGestureHelper = compositionRoot.tapGestureHelper

        screensNavigator = ScreensNavigator(findNavController())
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
        apodImage = getApodImage()
    }

    private fun getApodImage(): Bitmap {
        return requireArguments().getByteArray(ARG_APOD_IMAGE)?.toBitmap() as Bitmap
    }

    override fun onToolbarNavigationIconClicked() {
        screensNavigator.navigateUp()
    }

    override fun onToolbarActionExportApodImageClicked() {
        if (permissionsHelper.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            imageExporter.export(apodImage)
        else
            requestWriteToExternalStoragePermission()
    }

    private fun requestWriteToExternalStoragePermission() {
        permissionsHelper.requestPermission(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_EXTERNAL_STORAGE_REQUEST_CODE
        )
    }

    override fun onPermissionRequestResult(result: PermissionsHelper.PermissionResult) {
        when (result) {
            PermissionsHelper.PermissionResult.GRANTED ->
                imageExporter.export(apodImage)
            PermissionsHelper.PermissionResult.DENIED ->
                dialogManager.showGrantExternalStoragePermissionDialog()
            PermissionsHelper.PermissionResult.DENIED_PERMANENTLY ->
                dialogManager.showManuallyGrantPermissionDialog()
        }
    }

    override fun onScreenTapped() {
        if (screenHasBeenTapped)
            onScreenPreviouslyTapped()
        else
            onScreenNotPreviouslyTapped()
    }

    private fun onScreenPreviouslyTapped() {
        systemUIDelegate.showSystemUI()
        view.showToolbar()

        screenHasBeenTapped = false
    }

    private fun onScreenNotPreviouslyTapped() {
        systemUIDelegate.hideSystemUI()
        view.hideToolbar()

        screenHasBeenTapped = true
    }

    override fun onApodImageExportedSuccessfully() = messagesManager.showApodImageExportedMessage()

    override fun onExportApodImageFailed() = messagesManager.showUnexpectedErrorOccurredMessage()

    override fun onStart() {
        super.onStart()
        attachObservers()
        view.bind(apodImage)
    }

    private fun attachObservers() {
        view.addObserver(this)
        tapGestureHelper.addObserver(this)
        imageExporter.addObserver(this)
        permissionsHelper.addObserver(this)
    }

    override fun onStop() {
        super.onStop()
        removeObservers()
        systemUIDelegate.showSystemUI()
    }

    private fun removeObservers() {
        view.removeObserver(this)
        tapGestureHelper.removeObserver(this)
        permissionsHelper.removeObserver(this)
        imageExporter.removeObserver(this)
    }
}
