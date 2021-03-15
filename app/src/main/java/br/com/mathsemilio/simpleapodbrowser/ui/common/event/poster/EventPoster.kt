package br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable

class EventPoster : BaseObservable<EventPoster.EventListener>() {

    interface EventListener {
        fun onEvent(event: Any)
    }

    fun postEvent(event: Any) {
        listeners.forEach { it.onEvent(event) }
    }
}