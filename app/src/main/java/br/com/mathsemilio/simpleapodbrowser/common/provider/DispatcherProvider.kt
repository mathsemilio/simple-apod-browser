package br.com.mathsemilio.simpleapodbrowser.common.provider

import kotlinx.coroutines.Dispatchers

object DispatcherProvider {
    val background get() = Dispatchers.IO
    val main get() = Dispatchers.Main.immediate
}
