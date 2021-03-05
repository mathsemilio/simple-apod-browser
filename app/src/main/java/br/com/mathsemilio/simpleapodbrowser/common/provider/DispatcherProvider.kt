package br.com.mathsemilio.simpleapodbrowser.common.provider

import kotlinx.coroutines.Dispatchers

object DispatcherProvider {
    val BACKGROUND get() = Dispatchers.IO
    val MAIN get() = Dispatchers.Main.immediate
}
