package br.com.mathsemilio.simpleapodbrowser.ui

import android.os.Bundle
import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarActionClickEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.FragmentContainerHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavigationViewItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

class MainActivity : BaseActivity(),
    MainActivityContract.View.Listener,
    FragmentContainerHelper,
    EventPoster.EventListener {

    private lateinit var view: MainActivityView
    private lateinit var eventPoster: EventPoster
    private lateinit var screensNavigator: ScreensNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = compositionRoot.viewFactory.getMainActivityView(null)

        eventPoster = compositionRoot.eventPoster
        screensNavigator = compositionRoot.screensNavigator

        setContentView(view.rootView)

        if (savedInstanceState == null) {
            screensNavigator.navigateToAPoDListScreen().also {
                NavDestination.EXPLORE_SCREEN.let {
                    view.setToolbarTitleBasedOnDestination(it)
                    view.setToolbarMenuBasedOnDestination(it)
                }
            }
        }
    }

    override fun onBottomNavigationViewItemClicked(item: BottomNavigationViewItem) {
        when (item) {
            BottomNavigationViewItem.EXPLORE -> {
                screensNavigator.navigateToAPoDListScreen()
                NavDestination.EXPLORE_SCREEN.let {
                    view.setToolbarTitleBasedOnDestination(it)
                    view.setToolbarMenuBasedOnDestination(it)
                }
            }
            BottomNavigationViewItem.FAVORITES -> {
                screensNavigator.navigateToAPoDFavoritesScreen()
                NavDestination.FAVORITES_SCREEN.let {
                    view.setToolbarTitleBasedOnDestination(it)
                    view.setToolbarMenuBasedOnDestination(it)
                }
            }
        }
    }

    override fun onToolbarActionClicked(action: ToolbarAction) {
        when (action) {
            ToolbarAction.SEARCH_EXPLORE ->
                eventPoster.postEvent(ToolbarActionClickEvent(ToolbarAction.SEARCH_EXPLORE))
            ToolbarAction.SEARCH_FAVORITES ->
                eventPoster.postEvent(ToolbarActionClickEvent(ToolbarAction.SEARCH_FAVORITES))
            ToolbarAction.PICK_APOD_BY_DATE ->
                eventPoster.postEvent(ToolbarActionClickEvent(ToolbarAction.PICK_APOD_BY_DATE))
            ToolbarAction.ADD_TO_FAVORITES ->
                eventPoster.postEvent(ToolbarActionClickEvent(ToolbarAction.ADD_TO_FAVORITES))
        }
    }

    override fun getFragmentContainer(): FrameLayout {
        return view.fragmentContainer
    }

    override fun onEvent(event: Any) {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        view.addListener(this)
        eventPoster.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventPoster.removeListener(this)
        super.onStop()
    }
}