package br.com.mathsemilio.simpleapodbrowser

import android.app.Application
import br.com.mathsemilio.simpleapodbrowser.common.di.CompositionRoot

class SimpleAPoDBrowserApplication : Application() {

    private lateinit var _compositionRoot: CompositionRoot
    val compositionRoot get() = _compositionRoot

    override fun onCreate() {
        super.onCreate()
        _compositionRoot = CompositionRoot()
    }
}