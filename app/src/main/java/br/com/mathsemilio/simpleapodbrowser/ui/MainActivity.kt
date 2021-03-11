package br.com.mathsemilio.simpleapodbrowser.ui

import android.os.Bundle
import br.com.mathsemilio.simpleapodbrowser.common.event.NavigationEvent
import br.com.mathsemilio.simpleapodbrowser.common.event.ToolbarActionClickEvent
import br.com.mathsemilio.simpleapodbrowser.common.event.ToolbarSearchViewEvent
import br.com.mathsemilio.simpleapodbrowser.common.event.poster.EventPoster
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

    private lateinit var latestTopDestination: NavDestination

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = compositionRoot.viewFactory.getMainActivityView(null)

        eventPoster = compositionRoot.eventPoster
        screensNavigator = compositionRoot.screensNavigator

        setContentView(view.rootView)

        screensNavigator.navigateToAPoDListScreen()

        attachOnBackStackChangedListener()
    }

    private fun attachOnBackStackChangedListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                view.setToolbarMenuBasedOnDestination(latestTopDestination)
                view.setToolbarTitleBasedOnDestination(latestTopDestination)
                view.hideToolbarNaviagtionIcon()
            }
        }
    }

    override fun onBottomNavigationViewItemClicked(item: BottomNavigationViewItem) {
        when (item) {
            BottomNavigationViewItem.LATEST -> screensNavigator.navigateToAPoDListScreen()
            BottomNavigationViewItem.FAVORITES -> screensNavigator.navigateToAPoDFavoritesScreen()
        }
    }

    override fun onToolbarNavigationIconClicked() {
        supportFragmentManager.popBackStackImmediate()
    }

    override fun onToolbarActionClicked(action: ToolbarAction) {
        when (action) {
            ToolbarAction.PICK_APOD_BY_DATE ->
                eventPoster.postEvent(ToolbarActionClickEvent(ToolbarAction.PICK_APOD_BY_DATE))
            ToolbarAction.ADD_TO_FAVORITES ->
                eventPoster.postEvent(ToolbarActionClickEvent(ToolbarAction.ADD_TO_FAVORITES))
            ToolbarAction.GET_RANDOM_APOD ->
                eventPoster.postEvent(ToolbarActionClickEvent(ToolbarAction.GET_RANDOM_APOD))
            ToolbarAction.VISIT_APOD_WEBSITE ->
                eventPoster.postEvent(ToolbarActionClickEvent(ToolbarAction.VISIT_APOD_WEBSITE))
        }
    }

    override fun onSearchFavoritesSearchViewTextEntered(userInput: String) {
        eventPoster.postEvent(
            ToolbarSearchViewEvent(
                ToolbarSearchViewEvent.Event.TEXT_ENTERED_BY_USER,
                userInput
            )
        )
    }

    override val fragmentContainer get() = view.fragmentContainer

    override fun onEvent(event: Any) {
        when (event) {
            is NavigationEvent -> handleNavigationEvent(event.navEvent, event.navDestination)
        }
    }

    private fun handleNavigationEvent(event: NavigationEvent.Event?, destination: NavDestination) {
        if (event != null) {
            when (event) {
                NavigationEvent.Event.UPDATE_TOP_DESTINATION -> latestTopDestination = destination
            }
        }

        view.setToolbarTitleBasedOnDestination(destination)
        view.setToolbarMenuBasedOnDestination(destination)

        if (destination == NavDestination.APOD_DETAILS_SCREEN)
            view.showToolbarNavigationIcon()
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
