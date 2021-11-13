/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.simpleapodbrowser.domain.model

import com.google.gson.annotations.SerializedName

data class ApodSchema(
    @SerializedName("concept_tags")
    val conceptTags: Boolean,
    val title: String,
    val url: String,
    val date: String,
    @SerializedName("media_type")
    val mediaType: String,
    val explanation: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    val concepts: List<String>,
    val copyright: String?
)
