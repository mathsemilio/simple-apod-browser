package br.com.mathsemilio.simpleapodbrowser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.hideGroup
import br.com.mathsemilio.simpleapodbrowser.common.showGroup
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.TopDestination
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityViewImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    MainActivityView() {

    private var materialToolbarApp: MaterialToolbar
    private var frameLayoutScreenContainer: FrameLayout
    private var bottomNavigationViewApp: BottomNavigationView

    init {
        rootView = layoutInflater.inflate(R.layout.activity_main, parent, false)
        materialToolbarApp = findViewById(R.id.material_toolbar_app)
        frameLayoutScreenContainer = findViewById(R.id.frame_layout_screen_container)
        bottomNavigationViewApp = findViewById(R.id.bottom_navigation_view_app)
        setOnToolbarNavigationIconClickListener()
        setToolbarOnMenuItemClickListener()
        setBottomNavigationViewOnNavItemSelectedListener()
    }

    private fun setBottomNavigationViewOnNavItemSelectedListener() {
        bottomNavigationViewApp.setOnNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.bottom_nav_item_latest -> {
                    onBottomNavigationViewItemSelected(BottomNavItem.LATEST)
                    true
                }
                R.id.bottom_nav_item_favorite -> {
                    onBottomNavigationViewItemSelected(BottomNavItem.FAVORITES)
                    true
                }
                else -> false
            }
        }
    }

    private fun setOnToolbarNavigationIconClickListener() {
        materialToolbarApp.setNavigationOnClickListener { onToolbarNavigationIconClicked() }
    }

    private fun setToolbarOnMenuItemClickListener() {
        materialToolbarApp.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.toolbar_action_pick_date -> {
                    onToolbarActionClicked(ToolbarAction.PICK_APOD_DATE)
                    true
                }
                R.id.toolbar_action_get_random_apod -> {
                    onToolbarActionClicked(ToolbarAction.GET_RANDOM_APOD)
                    true
                }
                R.id.toolbar_action_add_to_favorites -> {
                    onToolbarActionClicked(ToolbarAction.ADD_TO_FAVORITES)
                    true
                }
                R.id.toolbar_action_visit_apod_website -> {
                    onToolbarActionClicked(ToolbarAction.VISIT_APOD_WEBSITE)
                    true
                }
                else -> false
            }
        }
    }

    override val screenContainer get() = frameLayoutScreenContainer

    override fun setToolbarNavigationIconVisibility(isVisible: Boolean) {
        when (isVisible) {
            true -> showToolbarNavigationIcon()
            else -> materialToolbarApp.navigationIcon = null
        }
    }

    private fun showToolbarNavigationIcon() {
        materialToolbarApp.navigationIcon = ResourcesCompat.getDrawable(
            context.resources, R.drawable.ic_baseline_arrow_back_24, null
        )
    }

    override fun setToolbarTitleForDestination(destination: NavDestination) {
        materialToolbarApp.title = when (destination) {
            NavDestination.DETAILS_SCREEN -> getString(R.string.details)
        }
    }

    override fun setToolbarTitleForTopDestination(topDestination: TopDestination) {
        materialToolbarApp.title = when (topDestination) {
            TopDestination.LATEST_SCREEN -> getString(R.string.latest)
            TopDestination.FAVORITES_SCREEN -> getString(R.string.favorites)
        }
    }

    override fun setToolbarMenuForDestination(destination: NavDestination) {
        val menu = materialToolbarApp.menu
        when (destination) {
            NavDestination.DETAILS_SCREEN -> {
                menu.hideGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_visit_apod_website
                )
                menu.showGroup(R.id.toolbar_menu_group_add_to_favorites)
            }
        }
    }

    override fun setToolbarMenuForTopDestination(topDestination: TopDestination) {
        val menu = materialToolbarApp.menu
        when (topDestination) {
            TopDestination.LATEST_SCREEN -> {
                menu.showGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_visit_apod_website
                )
            }
            TopDestination.FAVORITES_SCREEN -> {
                menu.showGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_visit_apod_website
                )
            }
        }
    }

    private fun onToolbarActionClicked(action: ToolbarAction) {
        listeners.forEach { listener ->
            listener.onToolbarActionClicked(action)
        }
    }

    private fun onToolbarNavigationIconClicked() {
        listeners.forEach { listener ->
            listener.onToolbarNavigationIconClicked()
        }
    }

    private fun onBottomNavigationViewItemSelected(item: BottomNavItem) {
        listeners.forEach { listener ->
            listener.onBottomNavigationViewItemClicked(item)
        }
    }
}