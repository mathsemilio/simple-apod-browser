package br.com.mathsemilio.simpleapodbrowser.ui

import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.simpleapodbrowser.SimpleAPoDBrowserApplication
import br.com.mathsemilio.simpleapodbrowser.common.di.ActivityCompositionRoot

abstract class BaseActivity : AppCompatActivity() {

    private val _compositionRoot by lazy {
        ActivityCompositionRoot(
            activity = this,
            (application as SimpleAPoDBrowserApplication).compositionRoot
        )
    }
    val compositionRoot get() = _compositionRoot
}