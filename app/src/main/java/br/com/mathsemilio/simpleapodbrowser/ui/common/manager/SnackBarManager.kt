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

package br.com.mathsemilio.simpleapodbrowser.ui.common.manager

import android.content.Context
import android.view.View
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.util.showSnackBarWithAction

class SnackBarManager(private val context: Context) {

    fun showFavoriteApodDeletedSuccessfullySnackBar(
        view: View,
        anchorView: View,
        onSnackBarActionClicked: () -> Unit,
        onSnackBarTimedOut: () -> Unit
    ) {
        context.showSnackBarWithAction(
            view,
            anchorView,
            context.getString(R.string.message_apod_removed_from_favorites),
            context.getString(R.string.undo),
            { onSnackBarActionClicked() },
            { onSnackBarTimedOut() }
        )
    }
}
