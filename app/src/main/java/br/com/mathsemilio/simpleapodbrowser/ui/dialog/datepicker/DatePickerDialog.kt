package br.com.mathsemilio.simpleapodbrowser.ui.dialog.datepicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.DateSetEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.BaseDialogFragment
import java.util.*

class DatePickerDialog : BaseDialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dateSet = calendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        val firstApodDate = calendar.apply {
            set(Calendar.YEAR, 1995)
            set(Calendar.MONTH, 6)
            set(Calendar.DAY_OF_MONTH, 16)
        }

        if (dateSet < firstApodDate)
            eventPoster.postEvent(DateSetEvent.InvalidDateSet)
        else
            eventPoster.postEvent(DateSetEvent.DateSet(dateSet.timeInMillis))
    }

    private lateinit var calendar: Calendar

    private lateinit var eventPoster: EventPoster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventPoster = compositionRoot.eventPoster
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, dayOfMonth)
    }
}