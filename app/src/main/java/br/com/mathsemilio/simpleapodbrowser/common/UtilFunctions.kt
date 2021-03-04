package br.com.mathsemilio.simpleapodbrowser.common

import android.view.Menu

fun Menu.hideGroup(vararg groupId: Int) = groupId.forEach { this.setGroupVisible(it, false) }

fun Menu.showGroup(vararg groupId: Int) = groupId.forEach { this.setGroupVisible(it, true) }