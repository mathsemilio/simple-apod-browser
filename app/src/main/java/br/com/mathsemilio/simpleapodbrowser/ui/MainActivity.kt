package br.com.mathsemilio.simpleapodbrowser.ui

import android.os.Bundle
import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventListener
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventPublisher
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventSubscriber
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.NavigationEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.FragmentContainerHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.SecondaryDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.TopDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

class MainActivity : BaseActivity(),
    MainActivityView.Listener,
    FragmentContainerHelper,
    EventListener {

    private lateinit var view: MainActivityViewImpl

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var eventSubscriber: EventSubscriber
    private lateinit var eventPublisher: EventPublisher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = compositionRoot.viewFactory.getMainActivityView(null)

        screensNavigator = compositionRoot.screensNavigator

        eventSubscriber = compositionRoot.eventSubscriber

        eventPublisher = compositionRoot.eventPublisher

        setContentView(view.rootView)

        screensNavigator.initialize(savedInstanceState)
    }

    override fun onToolbarNavigationIconClicked() {
        screensNavigator.navigateUp()
    }

    override fun onToolbarActionClicked(action: ToolbarAction) {
        eventPublisher.publishEvent(ToolbarEvent.ActionClicked(action))
    }

    override fun onBottomNavigationViewItemClicked(item: BottomNavItem) {
        screensNavigator.switchTopDestinationsBasedOnBottomNavItem(item)
    }

    override fun getFragmentContainer(): FrameLayout {
        return view.getScreenContainer()
    }

    override fun onEvent(event: Any) {
        when (event) {
            is NavigationEvent.ToTopDestination ->
                onNavigateToTopDestination(event.topDestination!!)
            is NavigationEvent.ToSecondaryDestination ->
                onNavigateToSecondaryDestination(event.secondaryDestination!!)
        }
    }

    private fun onNavigateToTopDestination(destination: TopDestination) {
        view.hideToolbarNavigationIcon()
        view.showBottomNavigationView()
        view.setToolbarTitleForTopDestination(destination)
        view.setToolbarMenuForTopDestination(destination)
    }

    private fun onNavigateToSecondaryDestination(destination: SecondaryDestination) {
        view.showToolbarNavigationIcon()
        view.hideBottomNavigationView()
        view.setToolbarTitleForSecondaryDestination(destination)
        view.setToolbarMenuForSecondaryDestination(destination)
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
        eventSubscriber.subscribe(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        super.onStop()
    }
}