package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import android.content.Context
import android.view.View
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.event.SnackBarActionEvent
import br.com.mathsemilio.simpleapodbrowser.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.common.showLongToast
import br.com.mathsemilio.simpleapodbrowser.common.showSnackBarWithAction

class MessagesManager(private val context: Context, private val eventPoster: EventPoster) {

    fun showUseCaseErrorMessage(errorMessage: String) {
        context.showLongToast(errorMessage)
    }

    fun showDeleteFavoriteAPoDSuccessMessage(view: View) {
        showSnackBarWithAction(
            view,
            context.getString(R.string.favorite_apod_deleted_successfully),
            context.getString(R.string.undo)
        ) {
            eventPoster.postEvent(SnackBarActionEvent(SnackBarActionEvent.Event.ACTION_UNDO_CLICKED))
        }
    }
}