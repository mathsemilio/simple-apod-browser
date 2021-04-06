package br.com.mathsemilio.simpleapodbrowser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentContainerView
import br.com.mathsemilio.simpleapodbrowser.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityViewImpl(inflater: LayoutInflater, parent: ViewGroup?) : MainActivityView() {

    private var materialToolbarApp: MaterialToolbar
    private var fragmentContainerApp: FragmentContainerView
    private var bottomNavigationViewApp: BottomNavigationView

    private var statusBarColor = 0

    init {
        rootView = inflater.inflate(R.layout.activity_main, parent, false)
        materialToolbarApp = findViewById(R.id.material_toolbar_app)
        fragmentContainerApp = findViewById(R.id.fragment_container_view_app)
        bottomNavigationViewApp = findViewById(R.id.bottom_navigation_view_app)
    }

    override val appToolbar get() = materialToolbarApp

    override val fragmentContainer get() = fragmentContainerApp

    override val bottomNavigationView get() = bottomNavigationViewApp

    override val previousStatusBarColor get() = statusBarColor

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

    override fun setPreviousStatusBarColor(color: Int) {
        statusBarColor = color
    }
}