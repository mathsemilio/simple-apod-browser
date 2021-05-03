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