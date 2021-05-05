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
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.common.toByteArray
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddFavoriteAPodUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class APoDDetailFragment : BaseFragment(),
    APoDDetailView.Listener,
    AddFavoriteAPodUseCase.Listener {

    private lateinit var view: APoDDetailView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var addFavoriteAPodUseCase: AddFavoriteAPodUseCase

    private lateinit var currentAPoD: APoD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        screensNavigator = compositionRoot.screensNavigator
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        addFavoriteAPodUseCase = compositionRoot.addFavoriteAPodUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getAPoDDetailsView(container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentAPoD = if (savedInstanceState != null)
            savedInstanceState.getSerializable(OUT_STATE_APOD) as APoD
        else
            getAPoD()
    }

    override fun onAPoDImageClicked(currentAPoDImage: Bitmap) {
        screensNavigator.toAPoDImageDetail(currentAPoDImage.toByteArray())
    }

    override fun onPlayIconClicked(videoUrl: String) {
        requireContext().launchWebPage(videoUrl)
    }

    private fun getAPoD(): APoD {
        return requireArguments().getSerializable(ARG_APOD) as APoD
    }

    private fun bindAPoD() {
        view.bindAPoDDetails(currentAPoD)
    }

    private fun addCurrentAPoDToFavorites() {
        coroutineScope.launch {
            addFavoriteAPodUseCase.addAPoDToFavorites(currentAPoD)
        }
    }

    override fun onApoDAddedToFavoritesSuccessfully() {
        messagesManager.showAPoDAddedToFavoritesSuccessfullyMessage()
    }

    override fun onAPoDIsAlreadyOnFavorites() {
        messagesManager.showAPoDAlreadyOnFavoritesMessage()
    }

    override fun onAddAPoDToFavoritesFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_apod_detail, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (!currentAPoD.isAPoDFavorite) {
            menu.findItem(R.id.toolbar_action_add_to_favorites).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_action_add_to_favorites -> {
                addCurrentAPoDToFavorites()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(OUT_STATE_APOD, currentAPoD)
    }

    override fun onStart() {
        view.addListener(this)
        addFavoriteAPodUseCase.addListener(this)
        bindAPoD()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        addFavoriteAPodUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}