package br.com.mathsemilio.simpleapodbrowser.ui

import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

interface MainActivityContract {

    interface View {
        interface Listener {
            fun onToolbarNavigationIconClicked()

            fun onToolbarActionClicked(action: ToolbarAction)
        }

        val screenContainer: FrameLayout

        fun setToolbarNavigationIconVisibility(isVisible: Boolean)

        fun setToolbarTitleBasedOnDestination(destination: NavDestination)

        fun setToolbarMenuGroupBasedOnDestination(destination: NavDestination)
    }
}