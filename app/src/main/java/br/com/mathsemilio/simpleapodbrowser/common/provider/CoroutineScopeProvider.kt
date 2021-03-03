package br.com.mathsemilio.simpleapodbrowser.common.provider

import kotlinx.coroutines.CoroutineScope

object CoroutineScopeProvider {
    val UIBoundScope get() = CoroutineScope(DispatcherProvider.main)
}