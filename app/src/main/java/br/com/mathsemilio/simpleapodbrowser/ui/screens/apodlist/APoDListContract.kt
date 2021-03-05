package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

interface APoDListContract {

    interface Screen {
        fun fetchApods()

        fun onFetchApodsStarted()

        fun onFetchApodsCompleted(apods: List<APoD>)

        fun onFetchApodsFailed(errorMessage: String)

        fun onToolbarActionPickApodByDateClicked()

        fun onToolbarActionGetRandomAPoDClicked()

        fun onToolbarActionVisitApodWebsiteClicked()

        fun handleToolbarActionClickEvent(action: ToolbarAction)
    }

    interface View {
        interface Listener {
            fun onApodClicked(apod: APoD)
            fun onScreenSwipedToRefresh()
        }

        fun bindApods(apods: List<APoD>)

        fun showProgressIndicator()

        fun hideProgressIndicator()

        fun onRefreshCompleted()
    }

    interface ListItem {
        interface Listener {
            fun onApodListItemClicked(apod: APoD)
        }

        fun bindAPoDDetails(apod: APoD)
    }
}