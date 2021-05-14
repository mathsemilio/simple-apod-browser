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