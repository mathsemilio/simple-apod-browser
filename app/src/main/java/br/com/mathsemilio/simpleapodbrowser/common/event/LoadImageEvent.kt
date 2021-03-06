package br.com.mathsemilio.simpleapodbrowser.common.event

class LoadImageEvent(
    private val _loadImageEvent: Event,
    private val _imageUrl: String
) {
    enum class Event { LOAD_IMAGE_FROM_URL }

    val loadImageEvent get() = _loadImageEvent
    val imageUrl get() = _imageUrl
}