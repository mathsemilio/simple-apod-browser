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

package br.com.mathsemilio.simpleapodbrowser.ui.common.view

import br.com.mathsemilio.simpleapodbrowser.common.observable.Observable

abstract class BaseObservableView<T> : Observable<T>, BaseView() {

    private val _listeners = HashSet<T>()

    protected val listeners
        get() = _listeners.toSet()

    override fun addObserver(observer: T) {
        _listeners.add(observer)
    }

    override fun removeObserver(observer: T) {
        _listeners.remove(observer)
    }

    protected inline fun notify(crossinline body: (T) -> Unit) {
        listeners.forEach { listener -> body(listener) }
    }
}
