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
import br.com.mathsemilio.simpleapodbrowser.common.util.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseActivity
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.StatusBarDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.SystemUIDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.HostLayoutHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.PermissionsHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.TapGestureHelper

class MainActivity : BaseActivity(), HostLayoutHelper, StatusBarDelegate, SystemUIDelegate {

    private lateinit var view: MainActivityViewImpl

    private lateinit var navController: NavController

    private lateinit var permissionsHelper: PermissionsHelper
    private lateinit var tapGestureHelper: TapGestureHelper

    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = MainActivityViewImpl(layoutInflater, parent = null)

        permissionsHelper = compositionRoot.permissionsHelper
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
                topLevelDestinationIds = setOf(R.id.ApodListScreen, R.id.ApodFavoritesScreen)
            )
        )

        view.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.ApodDetailScreen -> {
                    view.showToolbar()
                    view.hideBottomNavigationView()
                }
                R.id.ApodImageDetailScreen -> {
                    view.hideToolbar()
                }
                R.id.SettingsScreen -> {
                    view.hideBottomNavigationView()
                }
                else -> {
                    view.showToolbar()
                    view.showBottomNavigationView()
                }
            }
        }
    }

    override fun getNavHostFragment(): NavHostFragment {
        return supportFragmentManager.findFragmentById(
            R.id.fragment_container_view_app
        ) as NavHostFragment
    }

    override fun getFragmentContainer() = view.fragmentContainer

    override fun getBottomNavigationView() = view.bottomNavigationView

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
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