package br.com.mathsemilio.simpleapodbrowser.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.widget.Toast
import br.com.mathsemilio.simpleapodbrowser.R
import java.text.SimpleDateFormat
import java.util.*

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Menu.hideGroup(vararg groupId: Int) {
    groupId.forEach { id -> this.setGroupVisible(id, false) }
}

fun Menu.showGroup(vararg groupId: Int) {
    groupId.forEach { id -> this.setGroupVisible(id, true) }
}

fun Context.launchWebPage(url: String) {
    val page = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, page)
    if (intent.resolveActivity(this.packageManager) != null)
        this.startActivity(intent)
}

fun getWeekRangeDate(): String {
    val calendar = Calendar.getInstance()
    val dateToday = calendar.get(Calendar.DAY_OF_WEEK)

    val dayFromMonday = (dateToday + 7 - Calendar.MONDAY) % 7

    calendar.add(Calendar.DATE, -dayFromMonday - 1)

    return calendar.timeInMillis.formatTimeInMillis()
}

fun Long.formatTimeInMillis(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(this)
}

fun String.formatAPoDDate(context: Context): String {
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