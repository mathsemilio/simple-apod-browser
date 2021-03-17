package br.com.mathsemilio.simpleapodbrowser.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun showSnackBarWithAction(
    view: View,
    message: String,
    actionText: String,
    onActionClicked: () -> Unit
) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(actionText) { onActionClicked() }
}

fun launchWebPage(context: Context, url: String) {
    val page = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, page)
    if (intent.resolveActivity(context.packageManager) != null)
        context.startActivity(intent)
}

fun APoD.toFavoriteAPoD(): FavoriteAPoD {
    val title = this.title
    val date = this.date
    val url = this.url
    val mediaType = this.mediaType
    val explanation = this.explanation
    val thumbnailUrl = this.thumbnailUrl
    val copyright = this.copyright
    return FavoriteAPoD(title, date, url, mediaType, explanation, thumbnailUrl, copyright)
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