package br.com.mathsemilio.simpleapodbrowser.common.di

import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val retrofit get() = compositionRoot.retrofitBuilder.retroFit

    private val aPoDApi get() = compositionRoot.retrofitBuilder.apodApi

    private val _viewFactory by lazy {
        ViewFactory(activity.layoutInflater)
    }
    val viewFactory get() = _viewFactory
}
