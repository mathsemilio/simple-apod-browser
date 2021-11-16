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

package br.com.mathsemilio.simpleapodbrowser.ui.container

import android.graphics.Color
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
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.ContainerLayoutDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.SystemUIDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.PermissionsHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.TapGestureHelper
import br.com.mathsemilio.simpleapodbrowser.ui.container.view.MainActivityView
import br.com.mathsemilio.simpleapodbrowser.ui.container.view.MainActivityViewImpl

class MainActivity : BaseActivity(), SystemUIDelegate.Listener, ContainerLayoutDelegate {

    private lateinit var view: MainActivityView

    private lateinit var navController: NavController

    private lateinit var permissionsHelper: PermissionsHelper
    private lateinit var tapGestureHelper: TapGestureHelper

    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = MainActivityViewImpl(layoutInflater, parent = null)

        permissionsHelper = compositionRoot.permissionsHelper
        tapGestureHelper = compositionRoot.tapGestureHelper
        gestureDetector = compositionRoot.gestureDetectorCompat

        setContentView(view.rootView)

        view.bind(window)

        setupNavController()

        setupUIComponentsWithNavController()

        setOnDestinationChangedListener()
    }

    override val navHostFragment: NavHostFragment
        get() {
            return supportFragmentManager.findFragmentById(
                R.id.fragment_container_view_app
            ) as NavHostFragment
        }

    override val fragmentContainer
        get() = view.fragmentContainer

    override val bottomNavigationView
        get() = view.bottomNavigationView

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_container_view_app
        ) as NavHostFragment

        navController = navHostFragment.findNavController()
    }

    private fun setupUIComponentsWithNavController() {
        setSupportActionBar(view.toolbar)

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
                R.id.ApodDetailScreen -> onNavigateToApodDetailScreen()
                R.id.ApodImageDetailScreen -> onNavigateToImageDetailScreen()
                R.id.SettingsScreen -> view.hideBottomNavigationView()
                else -> {
                    view.showToolbar()
                    view.revertStatusBarColor()
                    view.showBottomNavigationView()
                }
            }
        }
    }

    private fun onNavigateToApodDetailScreen() {
        view.showToolbar()
        view.revertStatusBarColor()
        view.hideBottomNavigationView()
    }

    private fun onNavigateToImageDetailScreen() {
        view.hideToolbar()
        view.hideBottomNavigationView()
        view.setStatusBarColor(Color.BLACK)
    }

    override fun onShowSystemUIRequested() {
        view.showSystemUI()
    }

    override fun onHideSystemUIRequested() {
        view.hideSystemUI()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
