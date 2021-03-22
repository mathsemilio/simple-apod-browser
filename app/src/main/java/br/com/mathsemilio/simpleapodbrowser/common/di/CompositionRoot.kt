package br.com.mathsemilio.simpleapodbrowser.common.di

import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventBus
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventPublisher
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventSubscriber
import br.com.mathsemilio.simpleapodbrowser.common.provider.APIKeyProvider
import br.com.mathsemilio.simpleapodbrowser.networking.RetrofitBuilder

class CompositionRoot {

    private val _retrofitBuilder by lazy {
        RetrofitBuilder()
    }

    private val _eventBus by lazy {
        EventBus()
    }

    private val _eventPublisher by lazy {
        EventPublisher(_eventBus)
    }

    private val _eventSubscriber by lazy {
        EventSubscriber(_eventBus)
    }

    val apiKeyProvider get() = APIKeyProvider

    val eventPublisher get() = _eventPublisher

    val eventSubscriber get() = _eventSubscriber

    val retrofitBuilder get() = _retrofitBuilder
}