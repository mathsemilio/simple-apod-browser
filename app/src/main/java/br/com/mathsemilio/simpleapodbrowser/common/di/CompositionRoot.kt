package br.com.mathsemilio.simpleapodbrowser.common.di

import br.com.mathsemilio.simpleapodbrowser.networking.RetrofitBuilder

class CompositionRoot {

    private val _retrofitBuilder by lazy {
        RetrofitBuilder()
    }
    val retrofitBuilder get() = _retrofitBuilder
}
