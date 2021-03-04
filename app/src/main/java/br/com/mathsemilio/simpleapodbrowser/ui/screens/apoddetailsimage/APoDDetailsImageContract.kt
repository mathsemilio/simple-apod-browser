package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsimage

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

interface APoDDetailsImageContract {

    interface Screen {
        fun getAPoD(): APoD

        fun onToolbarActionAddToFavoritesClicked()
    }

    interface View {
        fun bindAPoDDetails(apod: APoD)
    }
}