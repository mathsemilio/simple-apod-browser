/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.simpleapodbrowser.ui.common.util

import android.net.Uri
import android.content.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.launchWebPage(url: String) {
    val page = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, page)

    if (intent.resolveActivity(this.packageManager) != null)
        this.startActivity(intent)
}

fun Fragment.launchWebPage(url: String) {
    val page = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, page)

    startActivity(intent)
}

inline fun SearchView.onQueryTextChangedListener(
    crossinline onQueryTextChanged: (String) -> Unit
) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = false

        override fun onQueryTextChange(newText: String?): Boolean {
            onQueryTextChanged(newText.orEmpty())
            return true
        }
    })
}
