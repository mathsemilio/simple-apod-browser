package br.com.mathsemilio.simpleapodbrowser.ui

import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavigationViewItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

interface MainActivityContract {

    interface View {
        interface Listener {
            fun onBottomNavigationViewItemClicked(item: BottomNavigationViewItem)
            fun onToolbarActionClicked(action: ToolbarAction)
        }

        val fragmentContainer: FrameLayout

        fun showToolbar()

        fun hideToolbar()

        fun setToolbarTitleBasedOnDestination(destination: NavDestination)

        fun setToolbarMenuBasedOnDestination(destination: NavDestination)
    }
}