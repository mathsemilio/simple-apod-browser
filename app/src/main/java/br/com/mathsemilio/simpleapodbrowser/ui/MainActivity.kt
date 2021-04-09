/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package br.com.mathsemilio.simpleapodbrowser.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.HostLayoutHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.TapGestureHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.StatusBarManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.SystemUIManager

class MainActivity : BaseActivity(),
    HostLayoutHelper,
    StatusBarManager,
    SystemUIManager {

    private lateinit var view: MainActivityView

    private lateinit var navController: NavController

    private lateinit var tapGestureHelper: TapGestureHelper

    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = compositionRoot.viewFactory.getMainActivityView(null)

        tapGestureHelper = compositionRoot.tapGestureHelper

        setContentView(view.rootView)

        setupNavController()

        setupUIComponentsWithNavController()

        setOnDestinationChangedListener()

        gestureDetector = GestureDetectorCompat(this, tapGestureHelper)
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

    override fun onSetStatusBarColor(color: Int) {
        view.setStatusBarColor(window, color)
    }

    override fun onRevertStatusBarColor() {
        view.revertStatusBarColor(window)
    }

    override fun onHideSystemUI() {
        view.hideSystemUI(window)
    }

    override fun onShowSystemUI() {
        view.showSystemUI(window)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
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