package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

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
        onSnackBarActionClicked: () -> Unit
    ) {
        context.showSnackBarWithAction(
            view,
            context.getString(R.string.message_apod_deleted_successfully),
            context.getString(R.string.undo)
        ) { onSnackBarActionClicked() }
    }

    fun showAPoDAddedToFavoritesUseCaseSuccessMessage() {
        context.showLongToast(context.getString(R.string.message_apod_added_to_favorites_completed))
    }
}