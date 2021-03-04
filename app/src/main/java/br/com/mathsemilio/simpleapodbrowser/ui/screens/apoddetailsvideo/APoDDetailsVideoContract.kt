package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

interface APoDDetailsVideoContract {

    interface Screen {
        fun getAPoD(): APoD

        fun onToolbarActionAddToFavoritesClicked()
    }

    interface View {
        interface Listener {
            fun onButtonPlayClicked()
        }

        fun bindAPoDDetails(aPoD: APoD)
    }
}