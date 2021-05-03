/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
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