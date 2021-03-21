package br.com.mathsemilio.simpleapodbrowser.ui

import android.os.Bundle
import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.NavigationEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.FragmentContainerHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.TopDestination

class MainActivity : BaseActivity(),
    MainActivityContract.View.Listener,
    FragmentContainerHelper,
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

        screensNavigator.initialize(savedInstanceState)
    }

    override fun onToolbarNavigationIconClicked() {
        screensNavigator.navigateUp()
    }

    override fun onToolbarActionClicked(action: ToolbarAction) {
        eventPoster.postEvent(ToolbarEvent.ActionClicked(action))
    }

    override fun getFragmentContainer(): FrameLayout {
        return view.screenContainer
    }

    override fun onEvent(event: Any) {
        when (event) {
            is NavigationEvent -> onNavigationEvent(event)
        }
    }

    private fun onNavigationEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.ToDestination ->
                event.destination?.let { onNavigateToDestination(it) }
            is NavigationEvent.ToTopDestination ->
                event.topDestination?.let { onNavigateToTopDestination(it) }
        }
    }

    private fun onNavigateToDestination(destination: NavDestination) {
        view.setToolbarNavigationIconVisibility(isVisible = true)
        view.setToolbarMenuForDestination(destination)
        view.setToolbarMenuForDestination(destination)
    }

    private fun onNavigateToTopDestination(topDestination: TopDestination) {
        view.setToolbarNavigationIconVisibility(isVisible = false)
        view.setToolbarTitleForTopDestination(topDestination)
        view.setToolbarMenuForTopDestination(topDestination)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        screensNavigator.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (!screensNavigator.navigateUp())
            super.onBackPressed()
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