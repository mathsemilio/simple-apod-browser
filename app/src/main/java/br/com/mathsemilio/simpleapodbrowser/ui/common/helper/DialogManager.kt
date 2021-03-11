package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import androidx.fragment.app.FragmentManager
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.datepicker.DatePickerDialog

class DialogManager(private val fragmentManager: FragmentManager) {

    fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog()
        datePickerDialog.show(fragmentManager, null)
    }
}