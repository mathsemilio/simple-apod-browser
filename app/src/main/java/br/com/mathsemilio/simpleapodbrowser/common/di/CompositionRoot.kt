package br.com.mathsemilio.simpleapodbrowser.common.di

import br.com.mathsemilio.simpleapodbrowser.common.provider.APIKeyProvider
import br.com.mathsemilio.simpleapodbrowser.networking.RetrofitBuilder
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster

class CompositionRoot {

    private val _retrofitBuilder by lazy {
        RetrofitBuilder()
    }
    private val _eventPoster by lazy {
        EventPoster()
    }

    val apiKeyProvider get() = APIKeyProvider

    val eventPoster get() = _eventPoster

    val retrofitBuilder get() = _retrofitBuilder
}