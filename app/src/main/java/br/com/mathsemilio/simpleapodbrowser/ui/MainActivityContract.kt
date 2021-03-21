package br.com.mathsemilio.simpleapodbrowser.ui

import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.TopDestination

interface MainActivityContract {

    interface View {
        interface Listener {
            fun onToolbarNavigationIconClicked()

            fun onToolbarActionClicked(action: ToolbarAction)
        }

        val screenContainer: FrameLayout

        fun setToolbarNavigationIconVisibility(isVisible: Boolean)

        fun setToolbarTitleForDestination(destination: NavDestination)

        fun setToolbarTitleForTopDestination(topDestination: TopDestination)

        fun setToolbarMenuForDestination(destination: NavDestination)

        fun setToolbarMenuForTopDestination(topDestination: TopDestination)
    }
}