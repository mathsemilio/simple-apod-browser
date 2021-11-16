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

package br.com.mathsemilio.simpleapodbrowser.ui.common.manager

import android.content.Context
import androidx.fragment.app.FragmentManager
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.datepicker.DatePickerDialog
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.infodialog.InfoDialogBuilder
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.promptdialog.PromptDialogBuilder

class DialogManager(
    private val fragmentManager: FragmentManager,
    private val context: Context
) {
    fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog()
        datePickerDialog.show(fragmentManager, null)
    }

    fun showGrantExternalStoragePermissionDialog() {
        val infoDialog = InfoDialogBuilder
            .withTitle(context.getString(R.string.dialog_title_grant_permission))
            .withMessage(context.getString(R.string.dialog_message_grant_external_storage_permission))
            .withPositiveButtonText(context.getString(R.string.dialog_button_text_ok))
            .build()

        infoDialog.show(fragmentManager, null)
    }

    fun showManuallyGrantPermissionDialog() {
        val infoDialog = InfoDialogBuilder
            .withTitle(context.getString(R.string.dialog_title_grant_permission))
            .withMessage(context.getString(R.string.dialog_message_enable_permission_manually))
            .withPositiveButtonText(context.getString(R.string.dialog_button_text_ok))
            .build()

        infoDialog.show(fragmentManager, null)
    }

    fun showClearImageCacheDialog() {
        val promptDialog = PromptDialogBuilder
            .withTitle(context.getString(R.string.dialog_title_clear_image_cache))
            .withMessage(context.getString(R.string.dialog_message_clear_image_cache))
            .withPositiveButtonText(context.getString(R.string.dialog_button_text_ok))
            .withNegativeButtonText(context.getString(R.string.dialog_button_text_cancel))
            .setIsCancelable(true)
            .build()

        promptDialog.show(fragmentManager, null)
    }

    fun showConfirmFavoriteApodDeletionDialog() {
        val promptDialog = PromptDialogBuilder
            .withTitle(context.getString(R.string.dialog_title_are_you_sure))
            .withMessage(context.getString(R.string.dialog_message_confirm_apod_deletion))
            .withPositiveButtonText(context.getString(R.string.dialog_button_text_delete))
            .withNegativeButtonText(context.getString(R.string.dialog_button_text_cancel))
            .setIsCancelable(true)
            .build()

        promptDialog.show(fragmentManager, null)
    }
}
