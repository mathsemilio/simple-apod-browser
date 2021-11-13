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

package br.com.mathsemilio.simpleapodbrowser.storage.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import br.com.mathsemilio.simpleapodbrowser.common.DEFAULT_DATE_RANGE_LAST_SEVEN_DAYS
import br.com.mathsemilio.simpleapodbrowser.common.DEFAULT_DATE_RANGE_LAST_THIRTY_DAYS
import br.com.mathsemilio.simpleapodbrowser.common.DEFAULT_DATE_RANGE_PREFERENCE_KEY

class PreferencesEndpoint(context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    val defaultDateRange
        get() = sharedPreferences.getString(
            DEFAULT_DATE_RANGE_PREFERENCE_KEY,
            DEFAULT_DATE_RANGE_LAST_SEVEN_DAYS
        )
}
