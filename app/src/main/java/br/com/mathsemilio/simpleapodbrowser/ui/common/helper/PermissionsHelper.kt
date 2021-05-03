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
package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable

class PermissionsHelper(private val activity: AppCompatActivity) :
    BaseObservable<PermissionsHelper.Listener>() {

    interface Listener {
        fun onPermissionRequestResult(result: PermissionResult)
    }

    sealed class PermissionResult {
        object Granted : PermissionResult()
        object Denied : PermissionResult()
        object DeniedPermanently : PermissionResult()
    }

    private var currentRequestCode = 0

    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(permission: Array<String>, requestCode: Int) {
        currentRequestCode = requestCode
        ActivityCompat.requestPermissions(activity, permission, requestCode)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        androidPermissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (androidPermissions.isEmpty() || grantResults.isEmpty())
            onPermissionResult(PermissionResult.Denied)

        when (requestCode) {
            currentRequestCode -> {
                when {
                    grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                        onPermissionResult(PermissionResult.Granted)
                    }
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        androidPermissions[0]
                    ) -> {
                        onPermissionResult(PermissionResult.Denied)
                    }
                    else -> {
                        onPermissionResult(PermissionResult.DeniedPermanently)
                    }
                }
            }
        }
    }

    private fun onPermissionResult(result: PermissionResult) {
        listeners.forEach { listener ->
            listener.onPermissionRequestResult(result)
        }
    }
}