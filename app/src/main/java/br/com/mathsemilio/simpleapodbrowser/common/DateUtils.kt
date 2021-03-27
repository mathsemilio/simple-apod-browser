package br.com.mathsemilio.simpleapodbrowser.common

import android.content.Context
import br.com.mathsemilio.simpleapodbrowser.R
import java.text.SimpleDateFormat
import java.util.*

fun getLastSevenDays(): String {
    val calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -6) }
    return calendar.timeInMillis.formatTimeInMillis()
}

fun Long.formatTimeInMillis(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(this)
}

fun String.formatDate(context: Context): String {
    val year = this.substring(0..3)
    val month = this.substring(5..6)
    val day = this.substring(8..9)
    val formattedDate = "${convertMonthNumberToString(month)} $day, $year"

    return context.getString(R.string.date, formattedDate)
}

fun convertMonthNumberToString(month: String): String {
    return when (month) {
        "01" -> "January"
        "02" -> "February"
        "03" -> "March"
        "04" -> "April"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "August"
        "09" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> throw IllegalArgumentException(INVALID_MONTH)
    }
}