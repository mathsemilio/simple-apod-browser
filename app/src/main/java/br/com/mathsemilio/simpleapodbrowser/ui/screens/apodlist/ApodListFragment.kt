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
import androidx.navigation.fragment.findNavController
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.*
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventListener
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventSubscriber
import br.com.mathsemilio.simpleapodbrowser.data.manager.PreferencesManager
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodBasedOnDateUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodsUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchRandomApodUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.DateSetEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.ApodListScreenView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.ApodListScreenViewImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ApodListFragment : BaseFragment(),
    ApodListScreenView.Listener,
    FetchApodsUseCase.Listener,
    FetchRandomApodUseCase.Listener,
    FetchApodBasedOnDateUseCase.Listener,
    EventListener {

    private lateinit var view: ApodListScreenView

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var dialogManager: DialogManager

    private lateinit var eventSubscriber: EventSubscriber

    private lateinit var fetchApodsUseCase: FetchApodsUseCase
    private lateinit var fetchRandomApodUseCase: FetchRandomApodUseCase
    private lateinit var fetchApodBasedOnDateUseCase: FetchApodBasedOnDateUseCase

    private val apods = mutableListOf<Apod>()

    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        preferencesManager = compositionRoot.preferencesManager
        screensNavigator = ScreensNavigator(findNavController())
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        dialogManager = compositionRoot.dialogManager
        eventSubscriber = compositionRoot.eventSubscriber
        fetchApodsUseCase = compositionRoot.fetchApodUseCase
        fetchRandomApodUseCase = compositionRoot.fetchRandomApodUseCase
        fetchApodBasedOnDateUseCase = compositionRoot.fetchApodBasedOnDateUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = ApodListScreenViewImpl(inflater, container)
        return view.rootView
    }

    override fun onApodClicked(apod: Apod) {
        screensNavigator.toApodDetailsScreen(apod)
    }

    override fun onScreenSwipedToRefresh() {
        fetchApods()
    }

    private fun fetchApods() {
        if (!isDataLoaded)
            coroutineScope.launch {
                view.showProgressIndicator()
                fetchApodsUseCase.fetchApodsBasedOnDateRange(getDefaultDateRange())
            }
        else
            view.bindApods(apods)
    }

    private fun getDefaultDateRange(): Int {
        return when (preferencesManager.defaultDateRange) {
            DEFAULT_DATE_RANGE_LAST_SEVEN_DAYS -> 6
            DEFAULT_DATE_RANGE_LAST_FOURTEEN_DAYS -> 13
            DEFAULT_DATE_RANGE_LAST_TWENTY_ONE_DAYS -> 20
            DEFAULT_DATE_RANGE_LAST_THIRTY_DAYS -> 29
            else -> throw IllegalArgumentException(ILLEGAL_DEFAULT_DATE_RANGE_EXCEPTION)
        }
    }

    override fun onFetchApodsCompleted(apods: List<Apod>) {
        isDataLoaded = true
        this.apods.addAll(apods)

        view.hideNetworkRequestErrorState()
        view.hideProgressIndicator()
        view.onRefreshCompleted()
        view.bindApods(apods)
    }

    override fun onFetchApodsFailed() {
        view.hideProgressIndicator()
        view.onRefreshCompleted()
        view.showNetworkRequestErrorState()
    }

    override fun onFetchRandomApodCompleted(randomApod: Apod) {
        screensNavigator.toApodDetailsScreen(randomApod)
    }

    override fun onFetchRandomApodFailed() {
        messagesManager.showCheckYourConnectionMessage()
    }

    override fun onFetchApodBasedOnDateCompleted(apod: Apod) {
        screensNavigator.toApodDetailsScreen(apod)
    }

    override fun onFetchApodBasedOnDateFailed() {
        messagesManager.showCheckYourConnectionMessage()
    }

    override fun onEvent(event: Any) {
        when (event) {
            is DateSetEvent -> onApodDateSetEvent(event)
        }
    }

    private fun onApodDateSetEvent(event: DateSetEvent) {
        when (event) {
            is DateSetEvent.DateSet -> coroutineScope.launch {
                fetchApodBasedOnDateUseCase.getApodBasedOnDate(event.dateInMillis)
            }
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
                getRandomApod()
                true
            }
            R.id.toolbar_action_settings -> {
                screensNavigator.toSettingsScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getRandomApod() {
        coroutineScope.launch {
            fetchRandomApodUseCase.getRandomAPoD()
        }
    }

    override fun onStart() {
        view.addListener(this)
        eventSubscriber.subscribe(this)
        fetchApodsUseCase.addListener(this)
        fetchRandomApodUseCase.addListener(this)
        fetchApodBasedOnDateUseCase.addListener(this)
        fetchApods()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        fetchApodsUseCase.removeListener(this)
        fetchRandomApodUseCase.removeListener(this)
        fetchApodBasedOnDateUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}