package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsimage

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

interface APoDDetailsImageContract {

    interface Screen {
        fun bindAPoD()

        fun onToolbarActionAddToFavoritesClicked()

        fun onToolbarActionClickEvent(action: ToolbarAction)
    }

    interface View {
        fun bindAPoDDetails(apod: APoD)
    }
}