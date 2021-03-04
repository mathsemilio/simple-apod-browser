package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import androidx.fragment.app.FragmentManager
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites.APoDFavoritesScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.APoDListScreen

class ScreensNavigator(
    private val fragmentManager: FragmentManager,
    private val fragmentContainerHelper: FragmentContainerHelper
) {
    fun navigateToAPoDListScreen() {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(fragmentContainerHelper.getFragmentContainer().id, APoDListScreen())
            commitNow()
        }
    }

    fun navigateToAPoDFavoritesScreen() {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(fragmentContainerHelper.getFragmentContainer().id, APoDFavoritesScreen())
            commitNow()
        }
    }
}