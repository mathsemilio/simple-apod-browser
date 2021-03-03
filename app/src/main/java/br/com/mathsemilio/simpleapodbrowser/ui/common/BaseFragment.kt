package br.com.mathsemilio.simpleapodbrowser.ui.common

import androidx.fragment.app.Fragment
import br.com.mathsemilio.simpleapodbrowser.common.di.ControllerCompositionRoot
import br.com.mathsemilio.simpleapodbrowser.ui.MainActivity

abstract class BaseFragment : Fragment() {

    private val _compositionRoot by lazy {
        ControllerCompositionRoot((requireActivity() as MainActivity).compositionRoot)
    }
    val compositionRoot get() = _compositionRoot
}