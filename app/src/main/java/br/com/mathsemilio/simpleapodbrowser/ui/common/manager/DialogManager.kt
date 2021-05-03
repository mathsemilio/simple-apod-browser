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
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogManager(private val fragmentManager: FragmentManager, private val context: Context) {

    fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog()
        datePickerDialog.show(fragmentManager, null)
    }

    fun showGrantExternalStoragePermissionDialog() {
        MaterialAlertDialogBuilder(context).apply {
            setTitle(context.getString(R.string.dialog_title_grant_permission))
            setMessage(context.getString(R.string.dialog_message_grant_external_storage_permission))
            setPositiveButton(context.getString(R.string.dialog_button_text_ok), null)
            setCancelable(false)
            show()
        }
    }

    fun showEnablePermissionsManuallyDialog() {
        MaterialAlertDialogBuilder(context).apply {
            setTitle(context.getString(R.string.dialog_title_grant_permission))
            setMessage(context.getString(R.string.dialog_message_enable_permission_manually))
            setPositiveButton(context.getString(R.string.dialog_button_text_ok), null)
            setCancelable(false)
            show()
        }
    }
}