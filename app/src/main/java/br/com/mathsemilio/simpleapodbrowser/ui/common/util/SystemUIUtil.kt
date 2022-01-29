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

package br.com.mathsemilio.simpleapodbrowser.ui.common.util

import android.view.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.view.doOnLayout

@RequiresApi(Build.VERSION_CODES.R)
fun View.showSystemUI() {
    this.doOnLayout { view ->
        view.windowInsetsController?.show(
            WindowInsets.Type.statusBars() and WindowInsets.Type.navigationBars()
        )
    }
}

fun Window.showSystemUI() {
    this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}

@RequiresApi(Build.VERSION_CODES.R)
fun View.hideSystemUI() {
    this.doOnLayout { view ->
        view.windowInsetsController?.hide(
            WindowInsets.Type.statusBars() and WindowInsets.Type.navigationBars()
        )
    }
}

fun Window.hideSystemUI() {
    this.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_IMMERSIVE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
}
