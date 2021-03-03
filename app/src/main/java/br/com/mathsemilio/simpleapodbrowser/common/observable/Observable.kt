package br.com.mathsemilio.simpleapodbrowser.common.observable

interface Observable<T> {
    fun addListener(listener: T)
    fun removeListener(listener: T)
}