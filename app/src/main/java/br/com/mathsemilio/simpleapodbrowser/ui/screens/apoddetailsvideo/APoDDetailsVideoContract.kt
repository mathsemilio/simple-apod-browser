package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

interface APoDDetailsVideoContract {

    interface Screen {
        fun getAPoD(): APoD

        fun bindAPoD()
    }

    interface View {
        fun bindAPoDDetails(aPoD: APoD)
    }
}