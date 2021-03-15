package br.com.mathsemilio.simpleapodbrowser.ui.common.event

sealed class DateSetEvent {
    data class DateSet(val dateSetInMillis: Long) : DateSetEvent()
    object InvalidDateSet : DateSetEvent()
}