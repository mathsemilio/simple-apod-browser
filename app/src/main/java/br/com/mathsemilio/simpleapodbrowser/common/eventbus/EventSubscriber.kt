package br.com.mathsemilio.simpleapodbrowser.common.eventbus

class EventSubscriber(private val eventBus: EventBus) {

    fun subscribe(listener: EventListener) = eventBus.addListener(listener)

    fun unsubscribe(listener: EventListener) = eventBus.removeListener(listener)
}