package br.com.mathsemilio.simpleapodbrowser.data.manager

import br.com.mathsemilio.simpleapodbrowser.storage.preferences.PreferencesEndpoint

class PreferencesManager(private val endpoint: PreferencesEndpoint) {

    val defaultDateRange get() = endpoint.defaultDateRange
}