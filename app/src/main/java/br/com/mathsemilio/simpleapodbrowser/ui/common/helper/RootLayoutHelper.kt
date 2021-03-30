package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

interface RootLayoutHelper {
    val fragmentContainer: FrameLayout
    val rootBottomNavigationView: BottomNavigationView
}