package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

interface APoDDetailsVideoContract {

    interface Screen {
        fun getAPoD(): APoD

        fun bindAPoD()

        fun onToolbarActionAddToFavoritesClicked()

        fun handleToolbarActionClickEvent(action: ToolbarAction)

        fun onAddFavoriteAPoDStarted()

        fun onAddFavoriteAPoDCompleted()

        fun onAddFavoriteAPoDFailed(errorMessage: String)
    }

    interface View {
        fun bindAPoDDetails(aPoD: APoD)
    }
}