package br.com.mathsemilio.simpleapodbrowser.ui

import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavigationViewItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

interface MainActivityContract {

    interface View {
        interface Listener {
            fun onBottomNavigationViewItemClicked(item: BottomNavigationViewItem)

            fun onToolbarNavigationIconClicked()

            fun onToolbarActionClicked(action: ToolbarAction)
        }

        val fragmentContainer: FrameLayout

        fun setToolbarTitleBasedOnDestination(destination: NavDestination)

        fun setToolbarMenuBasedOnDestination(destination: NavDestination)

        fun setToolbarNavigationIconVisibility(isVisible: Boolean)

        fun setBottomNavigationViewVisibility(isVisible: Boolean)
    }
}