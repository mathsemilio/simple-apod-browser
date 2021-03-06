package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import br.com.mathsemilio.simpleapodbrowser.common.event.SnackBarActionEvent
import br.com.mathsemilio.simpleapodbrowser.common.event.ToolbarSearchViewEvent
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

interface APoDFavoritesContract {

    interface Screen {
        fun fetchApods()

        fun onFetchFavoriteApodsStarted()

        fun onFetchFavoriteApodsCompleted(favoriteApods: List<FavoriteAPoD>)

        fun onFetchFavoriteApodsFailed(errorMessage: String)

        fun onDeleteFavoriteApodStarted()

        fun onDeleteFavoriteApodCompleted()

        fun onDeleteFavoriteApodFailed(errorMessage: String)

        fun onToolbarActionVisitApodWebsiteClicked()

        fun onToolbarSearchViewTextEntered(userInput: String)

        fun onSnackBarActionUndoClicked()

        fun handleToolbarSearchViewEvent(
            event: ToolbarSearchViewEvent.Event,
            textEnteredByUser: String
        )

        fun handleToolbarActionClickEvent(action: ToolbarAction)

        fun handleSnackBarActionEvent(event: SnackBarActionEvent.Event)
    }

    interface View {
        interface Listener {
            fun onFavoriteAPoDClicked(favoriteApod: FavoriteAPoD)
            fun onRemoveFavoriteAPoDIconClicked(favoriteApod: FavoriteAPoD)
        }

        fun bindFavoriteApods(favoritesApods: List<FavoriteAPoD>)

        fun showProgressIndicator()

        fun hideProgressIndicator()
    }

    interface ListItem {
        interface Listener {
            fun onFavoriteAPoDClicked(favoriteApod: FavoriteAPoD)
            fun onRemoveFavoriteAPoDIconClicked(favoriteApod: FavoriteAPoD)
        }

        fun bindAPoDDetails(favoriteApod: FavoriteAPoD)
    }
}