package br.com.mathsemilio.simpleapodbrowser.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.hideGroup
import br.com.mathsemilio.simpleapodbrowser.common.showGroup
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.SecondaryDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.TopDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityViewImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    MainActivityView() {

    private lateinit var materialToolbarApp: MaterialToolbar
    private lateinit var frameLayoutFragmentContainer: FrameLayout
    private lateinit var bottomNavigationViewApp: BottomNavigationView

    init {
        rootView = layoutInflater.inflate(R.layout.activity_main, parent, false)
        initializeViews()
        setOnToolbarNavigationIconClickListener()
        setToolbarOnMenuItemClickListener()
        setBottomNavigationViewOnNavItemSelectedListener()
    }

    private fun initializeViews() {
        materialToolbarApp = findViewById(R.id.material_toolbar_app)
        frameLayoutFragmentContainer = findViewById(R.id.frame_layout_fragment_container)
        bottomNavigationViewApp = findViewById(R.id.bottom_navigation_view_app)
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
        materialToolbarApp.setNavigationOnClickListener {
            onToolbarNavigationIconClicked()
        }
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

    override val rootBottomNavigationView: BottomNavigationView
        get() {
            return bottomNavigationViewApp
        }

    override val fragmentContainer: FrameLayout
        get() {
            return frameLayoutFragmentContainer
        }

    override fun showToolbarNavigationIcon() {
        materialToolbarApp.navigationIcon = ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.ic_baseline_arrow_back_24,
            null
        )
    }

    override fun hideToolbarNavigationIcon() {
        materialToolbarApp.navigationIcon = null
    }

    override fun showBottomNavigationView() {
        bottomNavigationViewApp.visibility = View.VISIBLE
    }

    override fun hideBottomNavigationView() {
        bottomNavigationViewApp.visibility = View.GONE
    }

    override fun setToolbarTitleForTopDestination(destination: TopDestination) {
        materialToolbarApp.title = when (destination) {
            TopDestination.LATEST_SCREEN -> getString(R.string.latest)
            TopDestination.FAVORITES_SCREEN -> getString(R.string.favorites)
        }
    }

    override fun setToolbarTitleForSecondaryDestination(destination: SecondaryDestination) {
        materialToolbarApp.title = when (destination) {
            SecondaryDestination.APOD_DETAILS -> getString(R.string.details)
            SecondaryDestination.FAVORITE_APOD_DETAILS -> getString(R.string.details)
        }
    }

    override fun setToolbarMenuForTopDestination(destination: TopDestination) {
        val menu = materialToolbarApp.menu
        when (destination) {
            TopDestination.LATEST_SCREEN -> {
                menu.showGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_visit_apod_website
                )
                menu.hideGroup(R.id.toolbar_menu_group_add_to_favorites)
            }
            TopDestination.FAVORITES_SCREEN -> {
                menu.showGroup(R.id.toolbar_menu_group_visit_apod_website)
                menu.hideGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_add_to_favorites,
                )
            }
        }
    }

    override fun setToolbarMenuForSecondaryDestination(destination: SecondaryDestination) {
        val menu = materialToolbarApp.menu
        when (destination) {
            SecondaryDestination.APOD_DETAILS -> {
                menu.hideGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_visit_apod_website
                )
                menu.showGroup(R.id.toolbar_menu_group_add_to_favorites)
            }
            SecondaryDestination.FAVORITE_APOD_DETAILS -> {
                menu.hideGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_visit_apod_website,
                    R.id.toolbar_menu_group_add_to_favorites
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