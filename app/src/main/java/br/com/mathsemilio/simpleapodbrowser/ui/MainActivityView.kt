package br.com.mathsemilio.simpleapodbrowser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavigationViewItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.bottomnavigationview.BaseBottomNavigationView

class MainActivityView(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    BaseBottomNavigationView<MainActivityContract.View.Listener>(layoutInflater, parent),
    MainActivityContract.View {

    private var frameLayoutFragmentContainer: FrameLayout

    init {
        appendRootView(layoutInflater.inflate(R.layout.activity_main, parent, false))
        frameLayoutFragmentContainer = findViewById(R.id.frame_layout_fragment_container)
    }

    override fun onToolbarNavigationIconClicked() {
        listeners.forEach { it.onToolbarNavigationIconClicked() }
    }

    override fun onToolbarActionClicked(action: ToolbarAction) {
        listeners.forEach { it.onToolbarActionClicked(action) }
    }

    override fun onBottomNavigationViewItemSelected(item: BottomNavigationViewItem) {
        listeners.forEach { it.onBottomNavigationViewItemClicked(item) }
    }

    override val fragmentContainer: FrameLayout
        get() = frameLayoutFragmentContainer

    override fun setToolbarTitleBasedOnDestination(destination: NavDestination) {
        when (destination) {
            NavDestination.LATEST_SCREEN ->
                setToolbarTitle(context.getString(R.string.latest))
            NavDestination.FAVORITES_SCREEN ->
                setToolbarTitle(context.getString(R.string.favorites))
            NavDestination.APOD_DETAILS_SCREEN ->
                setToolbarTitle(context.getString(R.string.details))
        }
    }

    override fun setToolbarMenuBasedOnDestination(destination: NavDestination) {
        when (destination) {
            NavDestination.LATEST_SCREEN -> {
                hideToolbarGroup(R.id.toolbar_menu_group_details)
                showToolbarGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_visit_apod_website
                )
            }
            NavDestination.FAVORITES_SCREEN -> {
                hideToolbarGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_details
                )
                showToolbarGroup(R.id.toolbar_menu_group_visit_apod_website)
            }
            NavDestination.APOD_DETAILS_SCREEN -> {
                hideToolbarGroup(
                    R.id.toolbar_menu_group_visit_apod_website,
                    R.id.toolbar_menu_group_latest
                )
                showToolbarGroup(R.id.toolbar_menu_group_details)
            }
        }
    }

    override fun setToolbarNavigationIconVisibility(isVisible: Boolean) {
        when (isVisible) {
            true -> showToolbarNavigationIcon()
            false -> hideToolbarNavigationIcon()
        }
    }

    override fun setBottomNavigationViewVisibility(isVisible: Boolean) {
        when (isVisible) {
            true -> showBottomNavigationView()
            false -> hideBottomNavigationView()
        }
    }
}