package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

interface APoDDetailsVideoContract {

    interface Screen {
        fun getAPoD(): APoD

        fun onToolbarActionAddToFavoritesClicked()

        fun handleToolbarActionClickEvent(action: ToolbarAction)
    }

    interface View {
        interface Listener {
            fun onButtonPlayClicked()
        }

        fun bindAPoDDetails(aPoD: APoD)
    }
}