package br.com.mathsemilio.simpleapodbrowser.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.google.android.material.snackbar.Snackbar

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
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(actionText
    ) { onActionClicked() }
}

fun launchWebPage(context: Context, url: String) {
    val page = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, page)
    if (intent.resolveActivity(context.packageManager) != null)
        context.startActivity(intent)
}

fun Menu.hideGroup(vararg groupId: Int) = groupId.forEach { this.setGroupVisible(it, false) }

fun Menu.showGroup(vararg groupId: Int) = groupId.forEach { this.setGroupVisible(it, true) }

inline fun SearchView.onQueryTextChanged(crossinline onTextChanged: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            onTextChanged(newText.orEmpty())
            return true
        }
    })
}