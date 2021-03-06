package br.com.mathsemilio.simpleapodbrowser.common.event

class SnackBarActionEvent(private val _actionEvent: Event) {

    enum class Event { ACTION_UNDO_CLICKED }

    val actionEvent get() = _actionEvent
}