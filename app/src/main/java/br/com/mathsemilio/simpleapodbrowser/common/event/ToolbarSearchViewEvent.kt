package br.com.mathsemilio.simpleapodbrowser.common.event

class ToolbarSearchViewEvent(
    private val _searchViewEvent: Event,
    private val _textEnteredByUser: String
) {
    enum class Event { TEXT_ENTERED_BY_USER }

    val searchViewEvent get() = _searchViewEvent
    val textEnteredByUser get() = _textEnteredByUser
}