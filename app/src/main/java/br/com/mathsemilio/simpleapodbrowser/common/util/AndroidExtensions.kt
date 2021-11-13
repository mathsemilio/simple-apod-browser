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

package br.com.mathsemilio.simpleapodbrowser.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
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
    crossinline onSnackBarActionClicked: () -> Unit,
    crossinline onSnackBarTimedOut: () -> Unit
) {
    Snackbar.make(this, view, message, Snackbar.LENGTH_LONG)
        .setAnchorView(anchorView)
        .setAction(actionMessage) { onSnackBarActionClicked() }
        .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT)
                    onSnackBarTimedOut()
            }

            override fun onShown(transientBottomBar: Snackbar?) {
                super.onShown(transientBottomBar)
            }
        }).show()
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

    if (intent.resolveActivity(requireContext().packageManager) != null)
        this.startActivity(intent)
}

inline fun SearchView.onQueryTextChangedListener(
    crossinline onQueryTextChanged: (String) -> Unit
) {
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

inline fun onRecyclerViewItemSwipedToTheLeft(
    crossinline onItemSwiped: (RecyclerView.ViewHolder) -> Unit
): ItemTouchHelper.SimpleCallback {
    return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onItemSwiped(viewHolder)
        }
    }
}
