package br.com.mathsemilio.simpleapodbrowser.ui.common.manager

import android.content.Context
import android.view.View
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.showLongToast
import br.com.mathsemilio.simpleapodbrowser.common.showSnackBarWithAction

class MessagesManager(private val context: Context) {

    fun showUnexpectedErrorOccurredMessage() {
        context.showLongToast(context.getString(R.string.message_unexpected_error_occurred))
    }

    fun showInvalidAPoDDateErrorMessage() {
        context.showLongToast(context.getString(R.string.message_invalid_apod_date))
    }

    fun showDeleteFavoriteAPoDUseCaseSuccessMessage(
        view: View,
        anchorView: View,
        onSnackBarActionClicked: () -> Unit
    ) {
        context.showSnackBarWithAction(
            view,
            anchorView,
            context.getString(R.string.message_apod_removed_from_favorites),
            context.getString(R.string.undo)
        ) { onSnackBarActionClicked() }
    }

    fun showAPoDAddedToFavoritesUseCaseSuccessMessage() {
        context.showLongToast(context.getString(R.string.message_apod_added_to_favorites_completed))
    }
}