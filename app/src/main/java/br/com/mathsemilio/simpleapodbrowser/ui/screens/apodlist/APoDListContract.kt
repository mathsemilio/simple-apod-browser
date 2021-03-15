package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import br.com.mathsemilio.simpleapodbrowser.ui.common.event.DateSetEvent
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

interface APoDListContract {

    interface Screen {
        fun fetchApods()

        fun onFetchApodsStarted()

        fun onFetchApodsCompleted(apods: List<APoD>)

        fun onFetchApodsFailed(errorCode: String)

        fun onToolbarActionPickApodByDateClicked()

        fun onToolbarActionGetRandomAPoDClicked()

        fun onToolbarActionVisitApodWebsiteClicked()

        fun onAPoDDatePicked(dateSet: Long)

        fun onInvalidAPoDDatePicked()

        fun handleToolbarActionClickEvent(action: ToolbarAction)

        fun handleDateSetEvent(event: DateSetEvent)
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

        fun showNetworkRequestErrorState(errorCode: String)

        fun hideNetworkRequestFailedState()
    }

    interface ListItem {
        interface Listener {
            fun onApodListItemClicked(apod: APoD)
        }

        fun bindAPoDDetails(apod: APoD)
    }
}