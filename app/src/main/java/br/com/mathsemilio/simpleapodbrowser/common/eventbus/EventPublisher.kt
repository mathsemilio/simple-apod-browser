package br.com.mathsemilio.simpleapodbrowser.common.eventbus

class EventPublisher(private val eventBus: EventBus) {

    fun publishEvent(event: Any) = eventBus.postEvent(event)
}