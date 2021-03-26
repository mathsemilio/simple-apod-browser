package br.com.mathsemilio.simpleapodbrowser.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.View
import android.widget.Toast
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoDSchema
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showSnackBarWithAction(
    view: View,
    message: String,
    actionMessage: String,
    onSnackBarActionClicked: () -> Unit
) {
    Snackbar.make(this, view, message, Snackbar.LENGTH_LONG).setAction(actionMessage) {
        onSnackBarActionClicked()
    }.show()
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

fun APoDSchema.toAPoD(): APoD {
    val title = this.title
    val url = this.url
    val date = this.date
    val mediaType = this.mediaType
    val explanation = this.explanation
    val thumbnailUrl = this.thumbnailUrl
    return APoD(title, url, date, mediaType, explanation, thumbnailUrl)
}

fun List<APoDSchema>.toAPoDList(): List<APoD> {
    val apodList = mutableListOf<APoD>()
    this.forEach { aPoDSchema ->
        apodList.add(aPoDSchema.toAPoD())
    }
    return apodList.toList()
}