package br.com.mathsemilio.simpleapodbrowser.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.View
import android.widget.Toast
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoDSchema
import com.google.android.material.snackbar.Snackbar

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

@SuppressLint("QueryPermissionsNeeded")
fun Context.launchWebPage(url: String) {
    val page = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, page)
    if (intent.resolveActivity(this.packageManager) != null)
        this.startActivity(intent)
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