package br.com.mathsemilio.simpleapodbrowser.ui.common.event

sealed class DateSetEvent {
    data class DateSet(val dateInMillis: Long) : DateSetEvent()
    object InvalidDateSet : DateSetEvent()
}