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

package br.com.mathsemilio.simpleapodbrowser.ui.container.view

import android.view.*
import android.os.Build
import android.graphics.Color
import androidx.core.view.isVisible
import br.com.mathsemilio.simpleapodbrowser.R
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.appbar.MaterialToolbar
import br.com.mathsemilio.simpleapodbrowser.ui.common.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityViewImpl(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : MainActivityView() {

    private lateinit var window: Window

    private lateinit var materialToolbarApp: MaterialToolbar
    private lateinit var fragmentContainerApp: FragmentContainerView
    private lateinit var bottomNavigationViewApp: BottomNavigationView

    private var previousStatusBarColor = 0

    init {
        rootView = layoutInflater.inflate(R.layout.activity_main, parent, false)

        initializeViews()
    }

    private fun initializeViews() {
        materialToolbarApp = rootView.findViewById(R.id.material_toolbar_app)
        fragmentContainerApp = rootView.findViewById(R.id.fragment_container_view_app)
        bottomNavigationViewApp = rootView.findViewById(R.id.bottom_navigation_view_app)
    }

    override val toolbar
        get() = materialToolbarApp

    override val fragmentContainer
        get() = fragmentContainerApp

    override val bottomNavigationView
        get() = bottomNavigationViewApp

    override val statusBarColor: Int
        get() = window.statusBarColor

    override fun bind(window: Window) {
        this.window = window
        previousStatusBarColor = window.statusBarColor
    }

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

    override fun setStatusBarColor(color: Int) {
        window.apply {
            previousStatusBarColor = statusBarColor
            statusBarColor = Color.TRANSPARENT
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = color
        }
    }

    override fun revertStatusBarColor() {
        window.statusBarColor = previousStatusBarColor
    }

    override fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            rootView.hideSystemUI()
        else
            window.hideSystemUI()
    }

    override fun showSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            rootView.showSystemUI()
        else
            window.showSystemUI()
    }
}
