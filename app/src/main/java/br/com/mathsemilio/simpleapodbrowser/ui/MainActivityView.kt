package br.com.mathsemilio.simpleapodbrowser.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.hideGroup
import br.com.mathsemilio.simpleapodbrowser.common.onQueryTextChanged
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
        setToolbarOnNavigationClickListener()
        setToolbarOnMenuItemClickListener()
        setToolbarSearchViewOnQueryTextListener()
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
                    onBottomNavViewItemSelected(BottomNavigationViewItem.LATEST)
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

    private fun setToolbarOnNavigationClickListener() {
        materialToolbarApp.setNavigationOnClickListener {
            onToolbarNavigationIconClicked()
        }
    }

    private fun setToolbarOnMenuItemClickListener() {
        materialToolbarApp.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.toolbar_action_pick_date -> {
                    onToolbarActionClicked(ToolbarAction.PICK_APOD_BY_DATE)
                    true
                }
                R.id.toolbar_action_add_to_favorite -> {
                    onToolbarActionClicked(ToolbarAction.ADD_TO_FAVORITES)
                    true
                }
                R.id.toolbar_action_get_random_apod -> {
                    onToolbarActionClicked(ToolbarAction.GET_RANDOM_APOD)
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

    private fun setToolbarSearchViewOnQueryTextListener() {
        val toolbarMenu = materialToolbarApp.menu

        val toolbarSearchWidget = toolbarMenu.findItem(R.id.toolbar_action_search_favorites)
        val searchView = toolbarSearchWidget.actionView as SearchView

        searchView.onQueryTextChanged { onSearchFavoritesSearchViewTextEntered(it) }
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
            NavDestination.LATEST_SCREEN ->
                materialToolbarApp.title = context.getString(R.string.latest)
            NavDestination.FAVORITES_SCREEN ->
                materialToolbarApp.title = context.getString(R.string.favorites)
            NavDestination.APOD_DETAILS_SCREEN ->
                materialToolbarApp.title = context.getString(R.string.details)
        }
    }

    override fun setToolbarMenuBasedOnDestination(destination: NavDestination) {
        val toolbarMenu = materialToolbarApp.menu
        when (destination) {
            NavDestination.LATEST_SCREEN -> {
                toolbarMenu.hideGroup(
                    R.id.toolbar_menu_group_favorites,
                    R.id.toolbar_menu_group_details
                )
                toolbarMenu.showGroup(R.id.toolbar_menu_group_latest)
            }
            NavDestination.FAVORITES_SCREEN -> {
                toolbarMenu.hideGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_details
                )
                toolbarMenu.showGroup(R.id.toolbar_menu_group_favorites)
            }
            NavDestination.APOD_DETAILS_SCREEN -> {
                toolbarMenu.hideGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_favorites,
                    R.id.toolbar_menu_group_visit_apod_website
                )
                toolbarMenu.showGroup(R.id.toolbar_menu_group_details)
            }
        }
    }

    override fun showToolbarNavigationIcon() {
        materialToolbarApp.navigationIcon = ResourcesCompat.getDrawable(
            context.resources, R.drawable.ic_baseline_arrow_back_24, null
        )
    }

    override fun hideToolbarNaviagtionIcon() {
        materialToolbarApp.navigationIcon = null
    }

    private fun onToolbarNavigationIconClicked() {
        listeners.forEach { it.onToolbarNavigationIconClicked() }
    }

    private fun onSearchFavoritesSearchViewTextEntered(input: String) {
        listeners.forEach { it.onSearchFavoritesSearchViewTextEntered(input) }
    }

    private fun onBottomNavViewItemSelected(item: BottomNavigationViewItem) {
        listeners.forEach { it.onBottomNavigationViewItemClicked(item) }
    }

    private fun onToolbarActionClicked(action: ToolbarAction) {
        listeners.forEach { it.onToolbarActionClicked(action) }
    }
}