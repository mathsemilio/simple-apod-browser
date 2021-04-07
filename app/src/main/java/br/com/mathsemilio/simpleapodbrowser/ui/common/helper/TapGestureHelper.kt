package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import android.view.GestureDetector
import android.view.MotionEvent
import br.com.mathsemilio.simpleapodbrowser.common.observable.Observable

class TapGestureHelper :
    Observable<TapGestureHelper.Listener>,
    GestureDetector.SimpleOnGestureListener() {

    interface Listener {
        fun onScreenTapped()
    }

    private val listeners = mutableSetOf<Listener>()

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        listeners.forEach { listener ->
            listener.onScreenTapped()
        }
        return true
    }

    override fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }
}