package br.com.mathsemilio.simpleapodbrowser.ui

import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.TopDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

abstract class MainActivityView : BaseObservableView<MainActivityView.Listener>() {

    interface Listener {
        fun onToolbarNavigationIconClicked()

        fun onToolbarActionClicked(action: ToolbarAction)

        fun onBottomNavigationViewItemClicked(item: BottomNavItem)
    }

    abstract val screenContainer: FrameLayout

    abstract fun setToolbarNavigationIconVisibility(isVisible: Boolean)

    abstract fun setToolbarTitleForDestination(destination: NavDestination)

    abstract fun setToolbarTitleForTopDestination(topDestination: TopDestination)

    abstract fun setToolbarMenuForDestination(destination: NavDestination)

    abstract fun setToolbarMenuForTopDestination(topDestination: TopDestination)
}