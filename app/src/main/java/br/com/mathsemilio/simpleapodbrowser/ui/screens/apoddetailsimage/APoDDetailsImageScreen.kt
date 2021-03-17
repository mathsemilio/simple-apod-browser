package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.common.UNKNOWN_TYPE
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.io.Serializable

class APoDDetailsImageScreen : BaseFragment(),
    APoDDetailsImageContract.Screen,
    AddFavoriteAPoDUseCase.Listener,
    EventPoster.EventListener {

    companion object {
        fun <T : Serializable> newInstance(apod: T): APoDDetailsImageScreen {
            val args = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
            val aPoDDetailsImageScreen = APoDDetailsImageScreen()
            aPoDDetailsImageScreen.arguments = args
            return aPoDDetailsImageScreen
        }
    }

    private lateinit var view: APoDDetailsImageView

    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var eventPoster: EventPoster

    private lateinit var addFavoriteAPoDUseCase: AddFavoriteAPoDUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        eventPoster = compositionRoot.eventPoster
        addFavoriteAPoDUseCase = compositionRoot.addFavoriteAPoDUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getApodDetailsImageView(container)
        return view.rootView
    }

    private inline fun <reified T : Serializable> getAPoD(): T {
        return when (T::class) {
            APoD::class -> arguments?.getSerializable(ARG_APOD) as T
            FavoriteAPoD::class -> arguments?.getSerializable(ARG_APOD) as T
            else -> throw RuntimeException(UNKNOWN_TYPE)
        }
    }

    override fun bindAPoD() {
        view.bindAPoDDetails(getAPoD())
    }

    override fun onToolbarActionAddToFavoritesClicked() {
        messagesManager.showAddFavoriteAPoDUseCaseAddMessage()
        coroutineScope.launch { addFavoriteAPoDUseCase.addFavoriteAPoD(getAPoD()) }
    }

    override fun onToolbarActionClickEvent(action: ToolbarAction) {
        when (action) {
            ToolbarAction.ADD_TO_FAVORITES -> onToolbarActionAddToFavoritesClicked()
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    override fun onFavoriteAPoDAddedSuccessfully() {
        messagesManager.showAddFavoriteAPoDUseCaseSuccessMessage()
    }

    override fun onFavoriteAPoDAddFailed(errorMessage: String) {
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ToolbarEvent -> onToolbarActionClickEvent(event.action)
        }
    }

    override fun onStart() {
        eventPoster.addListener(this)
        addFavoriteAPoDUseCase.addListener(this)
        bindAPoD()
        super.onStart()
    }

    override fun onStop() {
        eventPoster.removeListener(this)
        addFavoriteAPoDUseCase.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroyView()
    }
}