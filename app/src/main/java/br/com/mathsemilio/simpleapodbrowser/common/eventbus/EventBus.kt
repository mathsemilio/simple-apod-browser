package br.com.mathsemilio.simpleapodbrowser.common.eventbus

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable

class EventBus : BaseObservable<EventListener>() {

    fun postEvent(event: Any) {
        listeners.forEach { it.onEvent(event) }
    }
}