package br.com.mathsemilio.simpleapodbrowser.ui.dialog.datepicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import br.com.mathsemilio.simpleapodbrowser.common.FIRST_APOD_DATE_IN_MILLIS
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventPublisher
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.DateSetEvent
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.BaseDialogFragment
import java.util.*

class DatePickerDialog : BaseDialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dateSet = calendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        checkDateSet(dateSet.timeInMillis)
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

        return DatePickerDialog(requireContext(), this, year, month, dayOfMonth)
    }

    private fun checkDateSet(dateSetInMillis: Long) {
        when {
            dateSetInMillis > System.currentTimeMillis() ->
                eventPublisher.publishEvent(DateSetEvent.InvalidDateSet)
            dateSetInMillis < FIRST_APOD_DATE_IN_MILLIS ->
                eventPublisher.publishEvent(DateSetEvent.InvalidDateSet)
            else -> eventPublisher.publishEvent(DateSetEvent.DateSet(dateSetInMillis))
        }
    }
}