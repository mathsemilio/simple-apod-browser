package br.com.mathsemilio.simpleapodbrowser.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.hideGroup
import br.com.mathsemilio.simpleapodbrowser.common.showGroup
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavigationViewItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityView(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    BaseObservableView<MainActivityContract.View.Listener>(),
    MainActivityContract.View {

    private lateinit var materialToolbarApp: MaterialToolbar
    private lateinit var frameLayoutScreenContainer: FrameLayout
    private lateinit var bottomNavigationViewApp: BottomNavigationView

    init {
        rootView = layoutInflater.inflate(R.layout.activity_main, parent, false)
        initializeViews()
        setOnBottomNavViewItemSelectedListener()
        setToolbarOnMenuItemClickListener()
    }

    private fun initializeViews() {
        materialToolbarApp = findViewById(R.id.material_toolbar_app)
        frameLayoutScreenContainer = findViewById(R.id.frame_layout_screen_container)
        bottomNavigationViewApp = findViewById(R.id.bottom_navigation_view_app)
    }

    private fun setOnBottomNavViewItemSelectedListener() {
        bottomNavigationViewApp.setOnNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.bottom_navigation_view_item_explore -> {
                    onBottomNavViewItemSelected(BottomNavigationViewItem.EXPLORE)
                    true
                }
                R.id.bottom_navigation_view_item_favorites -> {
                    onBottomNavViewItemSelected(BottomNavigationViewItem.FAVORITES)
                    true
                }
                else -> false
            }
        }
    }

    private fun setToolbarOnMenuItemClickListener() {
        materialToolbarApp.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.toolbar_action_search_explore -> {
                    onToolbarActionClicked(ToolbarAction.SEARCH_EXPLORE)
                    true
                }
                R.id.toolbar_action_pick_date -> {
                    onToolbarActionClicked(ToolbarAction.PICK_APOD_BY_DATE)
                    true
                }
                R.id.toolbar_action_search_favorites -> {
                    onToolbarActionClicked(ToolbarAction.SEARCH_FAVORITES)
                    true
                }
                R.id.toolbar_action_add_to_favorite -> {
                    onToolbarActionClicked(ToolbarAction.ADD_TO_FAVORITES)
                    true
                }
                else -> false
            }
        }
    }

    override val fragmentContainer get() = frameLayoutScreenContainer

    override fun showToolbar() {
        materialToolbarApp.visibility = View.VISIBLE
    }

    override fun hideToolbar() {
        materialToolbarApp.visibility = View.GONE
    }

    override fun setToolbarTitleBasedOnDestination(destination: NavDestination) {
        when (destination) {
            NavDestination.EXPLORE_SCREEN ->
                materialToolbarApp.title = context.getString(R.string.explore)
            NavDestination.FAVORITES_SCREEN ->
                materialToolbarApp.title = context.getString(R.string.favorites)
            NavDestination.APOD_DETAILS_SCREEN ->
                materialToolbarApp.title = context.getString(R.string.details)
        }
    }

    override fun setToolbarMenuBasedOnDestination(destination: NavDestination) {
        val toolbarMenu = materialToolbarApp.menu
        when (destination) {
            NavDestination.EXPLORE_SCREEN -> {
                toolbarMenu.hideGroup(
                    R.id.toolbar_menu_group_favorites,
                    R.id.toolbar_menu_group_details
                )
                toolbarMenu.showGroup(R.id.toolbar_menu_group_explore)
            }
            NavDestination.FAVORITES_SCREEN -> {
                toolbarMenu.hideGroup(
                    R.id.toolbar_menu_group_explore,
                    R.id.toolbar_menu_group_details
                )
                toolbarMenu.showGroup(R.id.toolbar_menu_group_favorites)
            }
            NavDestination.APOD_DETAILS_SCREEN -> {
                toolbarMenu.hideGroup(
                    R.id.toolbar_menu_group_explore,
                    R.id.toolbar_menu_group_favorites
                )
                toolbarMenu.showGroup(R.id.toolbar_menu_group_details)
            }
        }
    }

    private fun onBottomNavViewItemSelected(item: BottomNavigationViewItem) {
        listeners.forEach { it.onBottomNavigationViewItemClicked(item) }
    }

    private fun onToolbarActionClicked(action: ToolbarAction) {
        listeners.forEach { it.onToolbarActionClicked(action) }
    }
}