package br.com.mathsemilio.simpleapodbrowser.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

inline fun Context.showSnackBarWithAction(
    view: View,
    anchorView: View,
    message: String,
    actionMessage: String,
    crossinline onSnackBarActionClicked: () -> Unit
) {
    Snackbar.make(this, view, message, Snackbar.LENGTH_LONG)
        .setAnchorView(anchorView)
        .setAction(actionMessage) { onSnackBarActionClicked() }
        .show()
}

@SuppressLint("QueryPermissionsNeeded")
fun Context.launchWebPage(url: String) {
    val page = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, page)
    if (intent.resolveActivity(this.packageManager) != null)
        this.startActivity(intent)
}