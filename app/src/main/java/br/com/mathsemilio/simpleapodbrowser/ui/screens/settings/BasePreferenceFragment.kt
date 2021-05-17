package br.com.mathsemilio.simpleapodbrowser.ui.screens.settings

import androidx.preference.PreferenceFragmentCompat
import br.com.mathsemilio.simpleapodbrowser.common.di.ControllerCompositionRoot
import br.com.mathsemilio.simpleapodbrowser.ui.MainActivity

abstract class BasePreferenceFragment : PreferenceFragmentCompat() {

    private val _compositionRoot by lazy {
        ControllerCompositionRoot((requireActivity() as MainActivity).compositionRoot)
    }
    val compositionRoot get() = _compositionRoot
}