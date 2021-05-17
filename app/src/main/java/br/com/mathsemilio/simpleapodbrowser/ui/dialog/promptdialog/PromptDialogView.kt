package br.com.mathsemilio.simpleapodbrowser.ui.dialog.promptdialog

import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

abstract class PromptDialogView : BaseObservableView<PromptDialogView.Listener>() {

    interface Listener {
        fun onPositiveButtonClicked()

        fun onNegativeButtonClicked()
    }

    abstract fun setTitle(title: String)

    abstract fun setMessage(message: String)

    abstract fun setPositiveButtonText(positiveButtonText: String)

    abstract fun setNegativeButtonText(negativeButtonText: String?)
}