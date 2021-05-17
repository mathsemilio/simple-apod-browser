package br.com.mathsemilio.simpleapodbrowser.ui.common.event

sealed class PromptDialogEvent {
    object PositiveButtonClicked : PromptDialogEvent()
    object NegativeButtonClicked : PromptDialogEvent()
}