package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import android.content.Context
import android.view.View
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.event.SnackBarActionEvent
import br.com.mathsemilio.simpleapodbrowser.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.common.showLongToast
import br.com.mathsemilio.simpleapodbrowser.common.showShortToast
import br.com.mathsemilio.simpleapodbrowser.common.showSnackBarWithAction

class MessagesManager(private val context: Context, private val eventPoster: EventPoster) {

    fun showUseCaseErrorMessage(errorMessage: String) {
        context.showLongToast(errorMessage)
    }

    fun showDeleteFavoriteAPoDSuccessMessage(view: View) {
        showSnackBarWithAction(
            view,
            context.getString(R.string.message_favorite_apod_deleted_successfully),
            context.getString(R.string.undo)
        ) {
            eventPoster.postEvent(SnackBarActionEvent(SnackBarActionEvent.Event.ACTION_UNDO_CLICKED))
        }
    }

    fun showAddFavoriteAPoDUseCaseAddMessage() {
        context.showShortToast(context.getString(R.string.message_adding_to_favorites))
    }

    fun showAddFavoriteAPoDUseCaseSuccessMessage() {
        context.showShortToast(context.getString(R.string.message_apod_added_to_favorites_successfully))
    }

    fun showInvalidAPoDDateErrorMessage() {
        context.showLongToast(context.getString(R.string.message_invalid_apod_date))
    }
}