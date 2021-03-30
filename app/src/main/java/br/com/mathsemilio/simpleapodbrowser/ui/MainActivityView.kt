package br.com.mathsemilio.simpleapodbrowser.ui

import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.SecondaryDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.TopDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class MainActivityView : BaseObservableView<MainActivityView.Listener>() {

    interface Listener {
        fun onToolbarNavigationIconClicked()

        fun onToolbarActionClicked(action: ToolbarAction)

        fun onBottomNavigationViewItemClicked(item: BottomNavItem)
    }

    abstract val rootBottomNavigationView: BottomNavigationView

    abstract val fragmentContainer: FrameLayout

    abstract fun showToolbarNavigationIcon()

    abstract fun hideToolbarNavigationIcon()

    abstract fun showBottomNavigationView()

    abstract fun hideBottomNavigationView()

    abstract fun setToolbarTitleForTopDestination(destination: TopDestination)

    abstract fun setToolbarTitleForSecondaryDestination(destination: SecondaryDestination)

    abstract fun setToolbarMenuForTopDestination(destination: TopDestination)

    abstract fun setToolbarMenuForSecondaryDestination(destination: SecondaryDestination)
}