package br.com.mathsemilio.simpleapodbrowser.common.di

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    val eventPoster get() = activityCompositionRoot.eventPoster

    val viewFactory get() = activityCompositionRoot.viewFactory
}