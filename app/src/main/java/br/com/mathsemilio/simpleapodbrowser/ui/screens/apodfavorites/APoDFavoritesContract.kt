package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

interface APoDFavoritesContract {

    interface Screen {
        fun fetchApods()

        fun onFetchApodsStarted()

        fun onFetchApodsCompleted(apods: List<APoD>)

        fun onFetchApodsFailed(errorMessage: String)

        fun onToolbarActionSearchClicked()

        fun handleToolbarActionClickEvent(action: ToolbarAction)
    }

    interface View {
        interface Listener {
            fun onFavoriteAPoDClicked(apod: APoD)
        }

        fun bindFavoriteApods(apods: List<APoD>)

        fun showProgressIndicator()

        fun hideProgressIndicator()
    }

    interface ListItem {
        interface Listener {
            fun onFavoriteAPoDClicked(apod: APoD)
        }

        fun bindAPoDDetails(apod: APoD)
    }
}