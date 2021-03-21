package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

interface APoDDetailContract {

    interface Screen {
        fun getAPoD(): APoD

        fun bindAPoD()
    }

    interface View {
        interface Listener {
            fun onPlayIconClicked(videoUrl: String)
        }

        fun bindAPoDDetails(apod: APoD)
    }
}