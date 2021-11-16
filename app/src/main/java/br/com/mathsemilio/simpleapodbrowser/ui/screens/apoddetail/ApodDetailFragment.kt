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
import androidx.navigation.fragment.findNavController
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.common.util.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.common.util.toByteArray
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddFavoriteApodUseCase.AddFavoriteApodResult
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteApodUseCase.*
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.view.ApodDetailView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.view.ApodDetailViewImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ApodDetailFragment : BaseFragment(), ApodDetailView.Listener {

    private lateinit var view: ApodDetailView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var addFavoriteApodUseCase: AddFavoriteApodUseCase
    private lateinit var deleteFavoriteApodUseCase: DeleteFavoriteApodUseCase

    private lateinit var apod: Apod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        screensNavigator = ScreensNavigator(findNavController())
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope

        addFavoriteApodUseCase = compositionRoot.addFavoriteApodUseCase
        deleteFavoriteApodUseCase = compositionRoot.deleteFavoriteApodUseCase
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

        apod = requireArguments().getSerializable(ARG_APOD) as Apod
    }

    override fun onApodImageClicked(apodImage: Bitmap) {
        screensNavigator.toApodImageDetail(apodImage.toByteArray())
    }

    override fun onPlayIconClicked(videoUrl: String) = launchWebPage(videoUrl)

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (apod.isFavorite) {
            menu.findItem(R.id.toolbar_action_delete_favorite_apod).isVisible = true
            menu.findItem(R.id.toolbar_action_add_to_favorites).isVisible = false
        } else {
            menu.findItem(R.id.toolbar_action_delete_favorite_apod).isVisible = false
            menu.findItem(R.id.toolbar_action_add_to_favorites).isVisible = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_apod_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_action_add_to_favorites -> {
                addApodToFavorites()
                true
            }
            R.id.toolbar_action_delete_favorite_apod -> {
                deleteFavoriteApod()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addApodToFavorites() {
        coroutineScope.launch {
            val result = addFavoriteApodUseCase.addApodToFavorites(apod)
            handleAddApodToFavoritesResult(result)
        }
    }

    private fun handleAddApodToFavoritesResult(result: AddFavoriteApodResult) {
        when (result) {
            AddFavoriteApodResult.Completed ->
                messagesManager.showApodAddedToFavoritesSuccessfullyMessage()
            AddFavoriteApodResult.AlreadyFavorite ->
                messagesManager.showApodAlreadyOnFavoritesMessage()
            AddFavoriteApodResult.Failed ->
                messagesManager.showUnexpectedErrorOccurredMessage()
        }
    }

    private fun deleteFavoriteApod() {
        coroutineScope.launch {
            val result = deleteFavoriteApodUseCase.deleteFavoriteApod(apod)
            handleDeleteFavoriteApodResult(result)
        }
    }

    private fun handleDeleteFavoriteApodResult(result: DeleteFavoriteApodResult) {
        when (result) {
            DeleteFavoriteApodResult.Completed ->
                TODO("Show confirm deletion dialog")
            DeleteFavoriteApodResult.Failed ->
                messagesManager.showUnexpectedErrorOccurredMessage()
        }
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
        view.bind(apod)
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
