package br.com.mathsemilio.simpleapodbrowser.ui.common.event

import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

sealed class ToolbarEvent(val action: ToolbarAction) {
    class ActionClicked(action: ToolbarAction) : ToolbarEvent(action)
}