package br.com.mathsemilio.simpleapodbrowser.common.di

import br.com.mathsemilio.simpleapodbrowser.networking.RetrofitBuilder
import br.com.mathsemilio.simpleapodbrowser.common.event.poster.EventPoster

class CompositionRoot {

    private val _retrofitBuilder by lazy {
        RetrofitBuilder()
    }
    val retrofitBuilder get() = _retrofitBuilder

    private val _eventPoster by lazy {
        EventPoster()
    }
    val eventPoster get() = _eventPoster
}