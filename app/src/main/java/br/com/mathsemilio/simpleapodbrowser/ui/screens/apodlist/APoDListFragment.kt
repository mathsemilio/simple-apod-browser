/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.os.Bundle
import android.view.*
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventListener
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventSubscriber
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchAPoDBasedOnDateUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchAPoDsUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchRandomAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.DateSetEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.APoDListScreenView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class APoDListFragment : BaseFragment(),
    APoDListScreenView.Listener,
    FetchAPoDsUseCase.Listener,
    FetchRandomAPoDUseCase.Listener,
    FetchAPoDBasedOnDateUseCase.Listener,
    EventListener {

    private lateinit var view: APoDListScreenView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var dialogManager: DialogManager

    private lateinit var eventSubscriber: EventSubscriber

    private lateinit var fetchAPoDsUseCase: FetchAPoDsUseCase
    private lateinit var fetchRandomAPoDUseCase: FetchRandomAPoDUseCase
    private lateinit var fetchAPoDBasedOnDateUseCase: FetchAPoDBasedOnDateUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        screensNavigator = compositionRoot.screensNavigator
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        dialogManager = compositionRoot.dialogManager
        eventSubscriber = compositionRoot.eventSubscriber
        fetchAPoDsUseCase = compositionRoot.fetchAPoDUseCase
        fetchRandomAPoDUseCase = compositionRoot.fetchRandomAPoDUseCase
        fetchAPoDBasedOnDateUseCase = compositionRoot.fetchAPoDBasedOnDateUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getAPoDListScreenView(container)
        return view.rootView
    }

    override fun onApodClicked(apod: APoD) {
        screensNavigator.toAPoDDetailsScreen(apod)
    }

    override fun onScreenSwipedToRefresh() {
        fetchApods()
    }

    private fun fetchApods() {
        coroutineScope.launch {
            view.showProgressIndicator()
            fetchAPoDsUseCase.fetchAPoDBasedOnDateRange()
        }
    }

    override fun onFetchAPoDBasedOnDateRangeCompleted(apods: List<APoD>) {
        view.hideNetworkRequestFailedState()
        view.hideProgressIndicator()
        view.onRefreshCompleted()
        view.bindApods(apods)
    }

    override fun onFetchAPoDError(errorMessage: String) {
        view.hideProgressIndicator()
        view.onRefreshCompleted()
        view.showNetworkRequestErrorState(errorMessage)
    }

    override fun onRandomAPoDFetchedSuccessfully(randomAPoD: APoD) {
        screensNavigator.toAPoDDetailsScreen(randomAPoD)
    }

    override fun onFetchRandomAPoDFailed(errorMessage: String) {
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onAPoDBasedOnDateFetchedSuccessfully(apod: APoD) {
        screensNavigator.toAPoDDetailsScreen(apod)
    }

    override fun onFetchAPoDBasedOnDateFailed(errorMessage: String) {
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onEvent(event: Any) {
        when (event) {
            is DateSetEvent -> onDateSetEvent(event)
        }
    }

    private fun onDateSetEvent(event: DateSetEvent) {
        when (event) {
            is DateSetEvent.DateSet -> coroutineScope.launch {
                fetchAPoDBasedOnDateUseCase.fetchAPoDBasedOnDate(event.dateInMillis)
            }
            DateSetEvent.InvalidDateSet ->
                messagesManager.showInvalidAPoDDateErrorMessage()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_apod_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_action_pick_date -> {
                dialogManager.showDatePickerDialog()
                true
            }
            R.id.toolbar_action_get_random_apod -> {
                getRandomAPoD()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getRandomAPoD() {
        coroutineScope.launch {
            fetchRandomAPoDUseCase.fetchRandomAPoD()
        }
    }

    override fun onStart() {
        view.addListener(this)
        eventSubscriber.subscribe(this)
        fetchAPoDsUseCase.addListener(this)
        fetchRandomAPoDUseCase.addListener(this)
        fetchAPoDBasedOnDateUseCase.addListener(this)
        fetchApods()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        fetchAPoDsUseCase.removeListener(this)
        fetchRandomAPoDUseCase.removeListener(this)
        fetchAPoDBasedOnDateUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}