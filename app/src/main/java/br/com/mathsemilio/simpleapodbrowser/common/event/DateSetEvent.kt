package br.com.mathsemilio.simpleapodbrowser.common.event

class DateSetEvent(private val _dateSetEvent: Event, private val _dateSet: Long? = null) {

    enum class Event { DATE_SET, INVALID_DATE_SET }

    val dateSetEvent get() = _dateSetEvent
    val dateSet get() = _dateSet
}