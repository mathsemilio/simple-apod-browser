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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.common.OUT_STATE_APOD
import br.com.mathsemilio.simpleapodbrowser.common.util.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.common.util.toByteArray
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ApodDetailFragment : BaseFragment(),
    ApodDetailView.Listener,
    AddFavoriteApodUseCase.Listener {

    private lateinit var view: ApodDetailView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var addFavoriteApodUseCase: AddFavoriteApodUseCase

    private lateinit var currentApod: Apod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        screensNavigator = compositionRoot.screensNavigator
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        addFavoriteApodUseCase = compositionRoot.addFavoriteApodUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = ApodDetailViewImpl(inflater, container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentApod = if (savedInstanceState != null)
            savedInstanceState.getSerializable(OUT_STATE_APOD) as Apod
        else
            requireArguments().getSerializable(ARG_APOD) as Apod
    }

    override fun onApodImageClicked(apodImage: Bitmap) {
        screensNavigator.toApodImageDetail(apodImage.toByteArray())
    }

    override fun onPlayIconClicked(videoUrl: String) = launchWebPage(videoUrl)

    override fun onAddApodToFavoritesCompleted() {
        messagesManager.showApodAddedToFavoritesSuccessfullyMessage()
    }

    override fun onApodIsAlreadyOnFavorites() {
        messagesManager.showApodAlreadyOnFavoritesMessage()
    }

    override fun onAddApodToFavoritesFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.toolbar_action_add_to_favorites).isVisible = !currentApod.isFavorite
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_apod_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_action_add_to_favorites -> {
                addCurrentApodToFavorites()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addCurrentApodToFavorites() {
        coroutineScope.launch {
            addFavoriteApodUseCase.addApodToFavorites(currentApod)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(OUT_STATE_APOD, currentApod)
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
        addFavoriteApodUseCase.addListener(this)
        view.bindApod(currentApod)
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
        addFavoriteApodUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}