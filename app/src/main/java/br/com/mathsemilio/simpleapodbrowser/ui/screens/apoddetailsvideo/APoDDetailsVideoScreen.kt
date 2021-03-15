package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.OperationResult
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.AddFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.io.Serializable

class APoDDetailsVideoScreen : BaseFragment(),
    APoDDetailsVideoContract.Screen,
    AddFavoriteAPoDUseCase.Listener,
    EventPoster.EventListener {

    companion object {
        fun <T : Serializable> newInstance(apod: T): APoDDetailsVideoScreen {
            val args = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
            val aPoDDetailsVideoScreen = APoDDetailsVideoScreen()
            aPoDDetailsVideoScreen.arguments = args
            return aPoDDetailsVideoScreen
        }
    }

    private lateinit var view: APoDDetailsVideoView

    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var eventPoster: EventPoster

    private lateinit var addFavoriteAPoDUseCase: AddFavoriteAPoDUseCase

    private lateinit var currentAPoD: APoD

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
        view = compositionRoot.viewFactory.getApodDetailsVideoView(container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentAPoD = getAPoD()
    }

    override fun getAPoD(): APoD {
        return arguments?.getSerializable(ARG_APOD) as APoD
    }

    override fun bindAPoD() {
        view.bindAPoDDetails(currentAPoD)
    }

    override fun onToolbarActionAddToFavoritesClicked() {
        coroutineScope.launch { addFavoriteAPoDUseCase.addFavoriteAPoD(currentAPoD) }
    }

    override fun handleToolbarActionClickEvent(action: ToolbarAction) {
        when (action) {
            ToolbarAction.ADD_TO_FAVORITES -> onToolbarActionAddToFavoritesClicked()
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    override fun onAddFavoriteAPodUseCaseEvent(result: OperationResult<Nothing>) {
        when (result) {
            OperationResult.OnStarted -> onAddFavoriteAPoDStarted()
            is OperationResult.OnCompleted -> onAddFavoriteAPoDCompleted()
            is OperationResult.OnError -> onAddFavoriteAPoDFailed(result.errorMessage!!)
        }
    }

    override fun onAddFavoriteAPoDStarted() {
        messagesManager.showAddFavoriteAPoDUseCaseAddMessage()
    }

    override fun onAddFavoriteAPoDCompleted() {
        messagesManager.showAddFavoriteAPoDUseCaseSuccessMessage()
    }

    override fun onAddFavoriteAPoDFailed(errorMessage: String) {
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ToolbarEvent -> handleToolbarActionClickEvent(event.action)
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