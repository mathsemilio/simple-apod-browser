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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.settings

import android.os.Bundle
import androidx.preference.*
import br.com.mathsemilio.simpleapodbrowser.BuildConfig
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.*
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.*
import br.com.mathsemilio.simpleapodbrowser.storage.preferences.PreferencesRepository
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.*
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.promptdialog.PromptDialogEvent
import kotlinx.coroutines.*

class SettingsFragment : BasePreferenceFragment(), EventListener {

    private lateinit var preferencesRepository: PreferencesRepository
    private lateinit var messagesManager: MessagesManager
    private lateinit var dialogManager: DialogManager

    private lateinit var eventSubscriber: EventSubscriber

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferencesRepository = compositionRoot.preferencesRepository
        messagesManager = compositionRoot.messagesManager
        dialogManager = compositionRoot.dialogManager

        eventSubscriber = compositionRoot.eventSubscriber
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_settings, rootKey)

        setupDefaultDateRangePreference()

        findPreference<Preference>(APP_VERSION_PREFERENCE_KEY)?.summary = BuildConfig.VERSION_NAME
    }

    private fun setupDefaultDateRangePreference() {
        findPreference<ListPreference>(DEFAULT_DATE_RANGE_PREFERENCE_KEY)?.setSummaryProvider {
            return@setSummaryProvider when (preferencesRepository.defaultDateRange) {
                DEFAULT_DATE_RANGE_LAST_SEVEN_DAYS -> getString(R.string.last_seven_days)
                DEFAULT_DATE_RANGE_LAST_FOURTEEN_DAYS -> getString(R.string.last_fourteen_days)
                DEFAULT_DATE_RANGE_LAST_TWENTY_ONE_DAYS -> getString(R.string.last_twenty_one_days)
                DEFAULT_DATE_RANGE_LAST_THIRTY_DAYS -> getString(R.string.last_thirty_days)
                else -> throw IllegalArgumentException(UNKNOWN_DEFAULT_DATE_RANGE)
            }
        }
    }

    override fun onEvent(event: Any) {
        if (event is PromptDialogEvent)
            handleClearImageCachePromptDialogEvent(event)
    }

    private fun handleClearImageCachePromptDialogEvent(event: PromptDialogEvent) {
        when (event) {
            PromptDialogEvent.PositiveButtonClicked -> onClearImageCacheDialogPositiveButtonClicked()
            PromptDialogEvent.NegativeButtonClicked -> { /* no-op required for this event */ }
        }
    }

    private fun onClearImageCacheDialogPositiveButtonClicked() {
        coroutineScope.launch {
            ImageResourceManager.clearLocalCache(requireContext())
            messagesManager.showImageCacheClearedMessage()
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if (preference.key == CLEAR_IMAGE_CACHE_PREFERENCE_KEY)
            dialogManager.showClearImageCacheDialog()

        return super.onPreferenceTreeClick(preference)
    }

    override fun onStart() {
        super.onStart()
        eventSubscriber.subscribe(this)
    }

    override fun onStop() {
        super.onStop()
        eventSubscriber.unsubscribe(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
