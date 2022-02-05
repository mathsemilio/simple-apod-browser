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

package br.com.mathsemilio.simpleapodbrowser.ui.dialog.datepicker

import java.util.*
import android.os.Bundle
import android.app.Dialog
import android.widget.DatePicker
import android.app.DatePickerDialog
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.BaseDialogFragment
import br.com.mathsemilio.simpleapodbrowser.common.FIRST_APOD_DATE_MILLIS
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventPublisher

class DatePickerDialog : BaseDialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dateSet = calendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        eventPublisher.publish(DateSetEvent.DateSet(dateSet.timeInMillis))
    }

    private lateinit var calendar: Calendar

    private lateinit var eventPublisher: EventPublisher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventPublisher = compositionRoot.eventPublisher
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()

        return DatePickerDialog(
            requireContext(),
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = FIRST_APOD_DATE_MILLIS
            datePicker.maxDate = System.currentTimeMillis()
        }
    }
}
