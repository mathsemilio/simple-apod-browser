package br.com.mathsemilio.simpleapodbrowser.ui.common.view.bottomnavigationview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavigationViewItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseBottomNavigationView<Listener>(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableView<Listener>() {

    private var materialToolbarApp: MaterialToolbar
    private var frameLayoutScreenContainer: FrameLayout
    private var bottomNavigationViewApp: BottomNavigationView

    init {
        rootView = layoutInflater.inflate(R.layout.base_bottom_navigation_view, parent, false)
        materialToolbarApp = findViewById(R.id.material_toolbar_app)
        frameLayoutScreenContainer = findViewById(R.id.frame_layout_screen_container)
        bottomNavigationViewApp = findViewById(R.id.bottom_navigation_view_app)
        setToolbarNavigationIconClickListener()
        setToolbarOnMenuItemClickListener()
        setBottomNavigationViewOnNavigationItemSelectedListener()
    }

    protected fun appendRootView(view: View) {
        frameLayoutScreenContainer.addView(view)
    }

    private fun setToolbarNavigationIconClickListener() {
        materialToolbarApp.setNavigationOnClickListener { onToolbarNavigationIconClicked() }
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

    private fun setBottomNavigationViewOnNavigationItemSelectedListener() {
        bottomNavigationViewApp.setOnNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.bottom_navigation_view_item_latest -> {
                    onBottomNavigationViewItemSelected(BottomNavigationViewItem.LATEST)
                    true
                }
                R.id.bottom_navigation_view_item_favorites -> {
                    onBottomNavigationViewItemSelected(BottomNavigationViewItem.FAVORITES)
                    true
                }
                else -> false
            }
        }
    }

    abstract fun onToolbarNavigationIconClicked()

    abstract fun onToolbarActionClicked(action: ToolbarAction)

    abstract fun onBottomNavigationViewItemSelected(item: BottomNavigationViewItem)

    protected fun showToolbarNavigationIcon() {
        materialToolbarApp.navigationIcon = ResourcesCompat.getDrawable(
            context.resources, R.drawable.ic_baseline_arrow_back_24, null
        )
    }

    protected fun hideToolbarNavigationIcon() {
        materialToolbarApp.navigationIcon = null
    }

    protected fun setToolbarTitle(title: String) {
        materialToolbarApp.title = title
    }

    protected fun hideToolbarGroup(vararg groupId: Int) {
        groupId.forEach { id -> materialToolbarApp.menu.setGroupVisible(id, false) }
    }

    protected fun showToolbarGroup(vararg groupId: Int) {
        groupId.forEach { id -> materialToolbarApp.menu.setGroupVisible(id, true) }
    }

    protected fun showBottomNavigationView() {
        bottomNavigationViewApp.visibility = View.VISIBLE
    }

    protected fun hideBottomNavigationView() {
        bottomNavigationViewApp.visibility = View.GONE
    }
}