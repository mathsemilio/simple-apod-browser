package br.com.mathsemilio.simpleapodbrowser.common.observable

abstract class BaseObservable<Listener> : Observable<Listener> {

    private val listenersSet = mutableSetOf<Listener>()

    override fun addListener(listener: Listener) {
        listenersSet.add(listener)
    }

    override fun removeListener(listener: Listener) {
        listenersSet.remove(listener)
    }

    protected val listeners get() = listenersSet.toSet()
}