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

import android.graphics.Color
import android.view.*
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentContainerView
import br.com.mathsemilio.simpleapodbrowser.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivityViewImpl(inflater: LayoutInflater, parent: ViewGroup?) : MainActivityView() {

    private var materialToolbarApp: MaterialToolbar
    private var fragmentContainerApp: FragmentContainerView
    private var bottomNavigationViewApp: BottomNavigationView

    private var previousStatusBarColor = 0

    init {
        rootView = inflater.inflate(R.layout.activity_main, parent, false)
        materialToolbarApp = findViewById(R.id.material_toolbar_app)
        fragmentContainerApp = findViewById(R.id.fragment_container_view_app)
        bottomNavigationViewApp = findViewById(R.id.bottom_navigation_view_app)
    }

    override val appToolbar get() = materialToolbarApp

    override val fragmentContainer get() = fragmentContainerApp

    override val bottomNavigationView get() = bottomNavigationViewApp

    override fun showToolbar() {
        materialToolbarApp.isVisible = true
    }

    override fun hideToolbar() {
        materialToolbarApp.isVisible = false
    }

    override fun showBottomNavigationView() {
        bottomNavigationViewApp.isVisible = true
    }

    override fun hideBottomNavigationView() {
        bottomNavigationViewApp.isVisible = false
    }

    override fun setStatusBarColor(window: Window, color: Int) {
        window.apply {
            previousStatusBarColor = statusBarColor
            statusBarColor = Color.TRANSPARENT
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = color
        }
    }

    override fun revertStatusBarColor(window: Window) {
        window.statusBarColor = previousStatusBarColor
    }

    override fun hideSystemUI(window: Window) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            rootView.doOnLayout { view ->
                view.windowInsetsController?.hide(
                    WindowInsets.Type.statusBars() and WindowInsets.Type.navigationBars()
                )
            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

    override fun showSystemUI(window: Window) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            rootView.doOnLayout { view ->
                view.windowInsetsController?.show(
                    WindowInsets.Type.statusBars() and WindowInsets.Type.navigationBars()
                )
            }
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
}