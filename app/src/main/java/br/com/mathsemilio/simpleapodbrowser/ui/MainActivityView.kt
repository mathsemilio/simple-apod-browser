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
package br.com.mathsemilio.simpleapodbrowser.ui

import android.view.Window
import androidx.fragment.app.FragmentContainerView
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class MainActivityView : BaseView() {

    abstract val appToolbar: MaterialToolbar

    abstract val fragmentContainer: FragmentContainerView

    abstract val bottomNavigationView: BottomNavigationView

    abstract fun showToolbar()

    abstract fun hideToolbar()

    abstract fun showBottomNavigationView()

    abstract fun hideBottomNavigationView()

    abstract fun setStatusBarColor(window: Window, color: Int)

    abstract fun revertStatusBarColor(window: Window)

    abstract fun hideSystemUI(window: Window)

    abstract fun showSystemUI(window: Window)
}