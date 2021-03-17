package br.com.mathsemilio.simpleapodbrowser.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class APoD(
    @SerializedName("concept_tags")
    val conceptTags: Boolean = true,
    val title: String,
    val date: String,
    val url: String,
    @SerializedName("media_type")
    val mediaType: String,
    val explanation: String,
    val concepts: List<String>,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    val copyright: String?
) : Serializable