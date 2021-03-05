package br.com.mathsemilio.simpleapodbrowser.common

import android.view.Menu
import androidx.appcompat.widget.SearchView

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