package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.common.OUT_STATE_APOD
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventListener
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventSubscriber
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddFavoriteAPodUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class APoDDetailScreen : BaseFragment(),
    APoDDetailView.Listener,
    AddFavoriteAPodUseCase.Listener,
    EventListener {

    companion object {
        fun newInstance(apod: APoD): APoDDetailScreen {
            val args = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
            val aPoDDetailsImageScreen = APoDDetailScreen()
            aPoDDetailsImageScreen.arguments = args
            return aPoDDetailsImageScreen
        }
    }

    private lateinit var view: APoDDetailViewImpl

    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var eventSubscriber: EventSubscriber

    private lateinit var addFavoriteAPodUseCase: AddFavoriteAPodUseCase

    private lateinit var currentAPoD: APoD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        eventSubscriber = compositionRoot.eventSubscriber
        addFavoriteAPodUseCase = compositionRoot.addFavoriteAPodUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getApodDetailsView(container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentAPoD = if (savedInstanceState != null)
            savedInstanceState.getSerializable(OUT_STATE_APOD) as APoD
        else
            getAPoD()
    }

    override fun onPlayIconClicked(videoUrl: String) {
        requireContext().launchWebPage(videoUrl)
    }

    private fun getAPoD(): APoD {
        return arguments?.getSerializable(ARG_APOD) as APoD
    }

    private fun bindAPoD() {
        view.bindAPoDDetails(currentAPoD)
    }

    private fun onToolbarActionClicked(action: ToolbarAction) {
        when (action) {
            ToolbarAction.ADD_TO_FAVORITES -> coroutineScope.launch {
                addFavoriteAPodUseCase.addAPoDToFavorites(currentAPoD)
            }
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    override fun onApoDAddedToFavoritesCompleted() {
        messagesManager.showAPoDAddedToFavoritesUseCaseSuccessMessage()
    }

    override fun onAddAPoDToFavoritesFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ToolbarEvent.ActionClicked -> onToolbarActionClicked(event.action)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(OUT_STATE_APOD, currentAPoD)
    }

    override fun onStart() {
        view.addListener(this)
        eventSubscriber.subscribe(this)
        addFavoriteAPodUseCase.addListener(this)
        bindAPoD()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        addFavoriteAPodUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}