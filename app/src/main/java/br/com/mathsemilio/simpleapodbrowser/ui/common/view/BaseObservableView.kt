package br.com.mathsemilio.simpleapodbrowser.ui.common.view

import android.content.Context
import android.view.View
import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable

abstract class BaseObservableView<Listener> : BaseObservable<Listener>(), IView {

    private lateinit var _rootView: View

    override var rootView: View
        get() = _rootView
        set(value) {
            _rootView = value
        }

    protected fun <T : View> findViewById(id: Int): T = _rootView.findViewById(id)

    protected val context: Context get() = _rootView.context
}