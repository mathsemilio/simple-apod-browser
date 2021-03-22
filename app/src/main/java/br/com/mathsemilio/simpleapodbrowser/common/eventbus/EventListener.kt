package br.com.mathsemilio.simpleapodbrowser.common.eventbus

interface EventListener {
    fun onEvent(event: Any)
}