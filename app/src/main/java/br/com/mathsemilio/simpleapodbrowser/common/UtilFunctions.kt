package br.com.mathsemilio.simpleapodbrowser.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.google.android.material.snackbar.Snackbar

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

inline fun SearchView.onQueryTextChangedListener(crossinline onQueryTextChanged: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            onQueryTextChanged(newText.orEmpty())
            return true
        }
    })
}