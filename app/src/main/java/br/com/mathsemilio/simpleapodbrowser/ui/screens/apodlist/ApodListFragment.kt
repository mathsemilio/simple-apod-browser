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
import br.com.mathsemilio.simpleapodbrowser.common.DEFAULT_DATE_RANGE_LAST_SEVEN_DAYS
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventListener
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventSubscriber
import br.com.mathsemilio.simpleapodbrowser.data.manager.PreferencesManager
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodBasedOnDateUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodBasedOnDateUseCase.FetchApodBasedOnDateResult
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodsUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodsUseCase.FetchApodsResult
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchRandomApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchRandomApodUseCase.FetchRandomApodResult
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.datepicker.DateSetEvent
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.ApodListScreenView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.ApodListScreenViewImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ApodListFragment : BaseFragment(), ApodListScreenView.Listener, EventListener {

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

    override fun onApodClicked(apod: Apod) = screensNavigator.toApodDetailsScreen(apod)

    override fun onScreenSwipedToRefresh() = fetchApods()

    private fun fetchApods() {
        coroutineScope.launch {
            view.showProgressIndicator()

            val result = fetchApodsUseCase.fetchApodsBasedOn(
                preferencesManager.defaultDateRange ?: DEFAULT_DATE_RANGE_LAST_SEVEN_DAYS
            )

            handleFetchApodsResult(result)
        }
    }

    private fun handleFetchApodsResult(result: FetchApodsResult) {
        when (result) {
            is FetchApodsResult.Completed -> onFetchApodsCompleted(result.apods)
            FetchApodsResult.Failed -> onFetchApodsFailed()
        }
    }

    private fun onFetchApodsCompleted(apods: List<Apod>?) {
        view.hideProgressIndicator()
        view.hideNetworkRequestErrorState()

        apods?.let { view.bind(it) }
    }

    private fun onFetchApodsFailed() {
        view.hideProgressIndicator()
        view.showNetworkRequestErrorState()
    }

    override fun onEvent(event: Any) {
        when (event) {
            is DateSetEvent -> onApodDateSetEvent(event)
        }
    }

    private fun onApodDateSetEvent(event: DateSetEvent) {
        when (event) {
            is DateSetEvent.DateSet -> {
                coroutineScope.launch {
                    val result = fetchApodBasedOnDateUseCase.fetchApodBasedOnDate(event.dateInMillis)
                    handleFetchApodBasedOnDateResult(result)
                }
            }
        }
    }

    private fun handleFetchApodBasedOnDateResult(result: FetchApodBasedOnDateResult) {
        when (result) {
            is FetchApodBasedOnDateResult.Completed -> {
                result.apod?.let { apod ->
                    screensNavigator.toApodDetailsScreen(apod)
                }
            }
            FetchApodBasedOnDateResult.Failed ->
                messagesManager.showCheckYourConnectionMessage()
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
                fetchRandomApod()
                true
            }
            R.id.toolbar_action_settings -> {
                screensNavigator.toSettingsScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchRandomApod() {
        coroutineScope.launch {
            val result = fetchRandomApodUseCase.fetchRandomApod()
            handleFetchRandomApodUseCaseResult(result)
        }
    }

    private fun handleFetchRandomApodUseCaseResult(result: FetchRandomApodResult) {
        when (result) {
            is FetchRandomApodResult.Completed -> {
                result.randomApod?.let { randomApod ->
                    screensNavigator.toApodDetailsScreen(randomApod)
                }
            }
            FetchRandomApodResult.Failed ->
                messagesManager.showCheckYourConnectionMessage()
        }
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
        eventSubscriber.subscribe(this)
        fetchApods()
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
