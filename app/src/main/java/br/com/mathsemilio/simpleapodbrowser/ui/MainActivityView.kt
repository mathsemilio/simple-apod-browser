package br.com.mathsemilio.simpleapodbrowser.ui

import androidx.fragment.app.FragmentContainerView
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class MainActivityView : BaseView() {

    abstract val appToolbar: MaterialToolbar

    abstract val fragmentContainer: FragmentContainerView

    abstract val bottomNavigationView: BottomNavigationView

    abstract val previousStatusBarColor: Int

    abstract fun showToolbar()

    abstract fun hideToolbar()

    abstract fun showBottomNavigationView()

    abstract fun hideBottomNavigationView()

    abstract fun setPreviousStatusBarColor(color: Int)
}