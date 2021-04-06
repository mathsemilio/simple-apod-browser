package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

interface HostLayoutHelper {
    val navHostFragment: NavHostFragment
    val fragmentContainer: FragmentContainerView
    val bottomNavigationView: BottomNavigationView
}