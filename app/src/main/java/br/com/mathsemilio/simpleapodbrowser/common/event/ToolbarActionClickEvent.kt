package br.com.mathsemilio.simpleapodbrowser.common.event

import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

class ToolbarActionClickEvent(private val _action: ToolbarAction) {
    val action get() = _action
}