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