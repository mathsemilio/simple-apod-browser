package br.com.mathsemilio.simpleapodbrowser.storage.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import br.com.mathsemilio.simpleapodbrowser.common.DEFAULT_DATE_RANGE_PREFERENCE_KEY

class PreferencesEndpoint(context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    val defaultDateRange get() = sharedPreferences.getString(DEFAULT_DATE_RANGE_PREFERENCE_KEY, "0")
}