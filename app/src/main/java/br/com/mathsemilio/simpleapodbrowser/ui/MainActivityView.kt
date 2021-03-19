package br.com.mathsemilio.simpleapodbrowser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.hideGroup
import br.com.mathsemilio.simpleapodbrowser.common.showGroup
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView
import com.google.android.material.appbar.MaterialToolbar

class MainActivityView(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    BaseObservableView<MainActivityContract.View.Listener>(),
    MainActivityContract.View {

    private var materialToolbarApp: MaterialToolbar
    private var frameLayoutScreenContainer: FrameLayout

    init {
        rootView = layoutInflater.inflate(R.layout.activity_main, parent, false)
        materialToolbarApp = findViewById(R.id.material_toolbar_app)
        frameLayoutScreenContainer = findViewById(R.id.frame_layout_screen_container)
        setOnToolbarNavigationIconClickListener()
        setToolbarOnMenuItemClickListener()
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
                R.id.toolbar_action_visit_apod_website -> {
                    onToolbarActionClicked(ToolbarAction.VISIT_APOD_WEBSITE)
                    true
                }
                else -> false
            }
        }
    }

    override val screenContainer: FrameLayout
        get() = frameLayoutScreenContainer

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

    override fun setToolbarTitleBasedOnDestination(destination: NavDestination) {
        materialToolbarApp.title = when (destination) {
            NavDestination.LATEST_SCREEN -> context.getString(R.string.latest)
            NavDestination.DETAILS_SCREEN -> context.getString(R.string.details)
        }
    }

    override fun setToolbarMenuGroupBasedOnDestination(destination: NavDestination) {
        val toolbarMenu = materialToolbarApp.menu
        when (destination) {
            NavDestination.LATEST_SCREEN -> {
                toolbarMenu.showGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_visit_apod_website
                )
            }
            NavDestination.DETAILS_SCREEN -> {
                toolbarMenu.hideGroup(
                    R.id.toolbar_menu_group_latest,
                    R.id.toolbar_menu_group_visit_apod_website
                )
            }
        }
    }

    private fun onToolbarActionClicked(action: ToolbarAction) {
        listeners.forEach { it.onToolbarActionClicked(action) }
    }

    private fun onToolbarNavigationIconClicked() {
        listeners.forEach { it.onToolbarNavigationIconClicked() }
    }
}