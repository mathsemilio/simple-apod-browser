package br.com.mathsemilio.simpleapodbrowser.ui.common.event

sealed class SearchViewEvent(val input: String) {
    class TextEntered(input: String) : SearchViewEvent(input)
}