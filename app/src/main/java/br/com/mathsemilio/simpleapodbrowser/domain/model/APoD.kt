package br.com.mathsemilio.simpleapodbrowser.domain.model

import java.io.Serializable

data class APoD(
    val title: String,
    val url: String,
    val date: String,
    val mediaType: String,
    val explanation: String,
    val thumbnailUrl: String?,
) : Serializable