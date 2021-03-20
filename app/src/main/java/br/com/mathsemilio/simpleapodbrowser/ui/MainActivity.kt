package br.com.mathsemilio.simpleapodbrowser.ui

import android.os.Bundle
import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.NavigationEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.FragmentContainerManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

class MainActivity : BaseActivity(),
    MainActivityContract.View.Listener,
    FragmentContainerManager,
    EventPoster.EventListener {

    private lateinit var view: MainActivityView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var eventPoster: EventPoster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = compositionRoot.viewFactory.getMainActivityView(null)

        screensNavigator = compositionRoot.screensNavigator

        eventPoster = compositionRoot.eventPoster

        setContentView(view.rootView)

        screensNavigator.navigateToLatestScreen()

        attachOnBackStackChangedListener()
    }

    private fun attachOnBackStackChangedListener() {
        supportFragmentManager.apply {
            addOnBackStackChangedListener {
                if (backStackEntryCount == 0) {
                    view.setToolbarNavigationIconVisibility(isVisible = false)
                    view.setToolbarTitleBasedOnDestination(NavDestination.LATEST_SCREEN)
                    view.setToolbarMenuGroupBasedOnDestination(NavDestination.LATEST_SCREEN)
                }
            }
        }
    }

    override fun onToolbarNavigationIconClicked() {
        supportFragmentManager.popBackStackImmediate()
    }

    override fun onToolbarActionClicked(action: ToolbarAction) {
        eventPoster.postEvent(ToolbarEvent.ActionClicked(action))
    }

    override fun getFragmentContainer(): FrameLayout {
        return view.screenContainer
    }

    override fun onEvent(event: Any) {
        when (event) {
            is NavigationEvent.OnNavigate -> onNavigationEvent(event.destination)
        }
    }

    private fun onNavigationEvent(destination: NavDestination) {
        when (destination) {
            NavDestination.LATEST_SCREEN -> {
                view.setToolbarNavigationIconVisibility(isVisible = false)
                view.setToolbarTitleBasedOnDestination(NavDestination.LATEST_SCREEN)
                view.setToolbarMenuGroupBasedOnDestination(NavDestination.LATEST_SCREEN)
            }
            NavDestination.DETAILS_SCREEN -> {
                view.setToolbarNavigationIconVisibility(isVisible = true)
                view.setToolbarTitleBasedOnDestination(NavDestination.DETAILS_SCREEN)
                view.setToolbarMenuGroupBasedOnDestination(NavDestination.DETAILS_SCREEN)
            }
        }
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