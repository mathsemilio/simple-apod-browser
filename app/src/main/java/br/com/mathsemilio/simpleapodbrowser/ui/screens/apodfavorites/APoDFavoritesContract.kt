package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.SnackBarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

interface APoDFavoritesContract {

    interface Screen {
        fun fetchFavoriteApods()

        fun onToolbarActionVisitApodWebsiteClicked()

        fun onToolbarActionClickEvent(action: ToolbarAction)

        fun onSnackBarActionEvent(event: SnackBarEvent)
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