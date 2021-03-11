package br.com.mathsemilio.simpleapodbrowser.ui.dialog

import androidx.fragment.app.DialogFragment
import br.com.mathsemilio.simpleapodbrowser.common.di.ControllerCompositionRoot
import br.com.mathsemilio.simpleapodbrowser.ui.MainActivity

abstract class BaseDialogFragment : DialogFragment() {

    private val _compositionRoot by lazy {
        ControllerCompositionRoot((requireActivity() as MainActivity).compositionRoot)
    }
    val compositionRoot get() = _compositionRoot
}