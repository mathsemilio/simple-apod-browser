package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import android.content.Context
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.showLongToast

class MessagesManager(private val context: Context) {

    fun showInvalidAPoDDateErrorMessage() {
        context.showLongToast(context.getString(R.string.message_invalid_apod_date))
    }
}