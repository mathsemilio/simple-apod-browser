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

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import br.com.mathsemilio.simpleapodbrowser.common.FIRST_APOD_DATE_IN_MILLIS
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventPublisher
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.BaseDialogFragment
import java.util.*

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
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, dayOfMonth).apply {
            datePicker.apply {
                minDate = FIRST_APOD_DATE_IN_MILLIS
                maxDate = System.currentTimeMillis()
            }
        }
    }
}
