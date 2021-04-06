package br.com.mathsemilio.simpleapodbrowser.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.HostLayoutHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.StatusBarManager

class MainActivity : BaseActivity(),
    HostLayoutHelper,
    StatusBarManager {

    private lateinit var view: MainActivityView

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = compositionRoot.viewFactory.getMainActivityView(null)

        setContentView(view.rootView)

        setupNavController()

        setupUIComponentsWithNavController()

        setOnDestinationChangedListener()
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_container_view_app
        ) as NavHostFragment

        navController = navHostFragment.findNavController()
    }

    private fun setupUIComponentsWithNavController() {
        setSupportActionBar(view.appToolbar)

        setupActionBarWithNavController(
            navController,
            AppBarConfiguration(
                topLevelDestinationIds = setOf(R.id.APoDListScreen, R.id.APoDFavoritesScreen)
            )
        )

        view.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.APoDDetailScreen -> {
                    view.showToolbar()
                    view.hideBottomNavigationView()
                }
                R.id.APoDImageDetailScreen ->
                    view.hideToolbar()
                else -> {
                    view.showToolbar()
                    view.showBottomNavigationView()
                }
            }
        }
    }

    override val navHostFragment: NavHostFragment
        get() {
            return supportFragmentManager.findFragmentById(
                R.id.fragment_container_view_app
            ) as NavHostFragment
        }

    override val fragmentContainer get() = view.fragmentContainer

    override val bottomNavigationView get() = view.bottomNavigationView

    override fun setStatusBarColor(color: Int) {
        window.apply {
            view.setPreviousStatusBarColor(statusBarColor)
            statusBarColor = Color.TRANSPARENT
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = color
        }
    }

    override fun revertStatusBarColor() {
        window.statusBarColor = view.previousStatusBarColor
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_app, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_global_action_visit_apod_website -> {
                launchWebPage(getString(R.string.apod_website_url))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}