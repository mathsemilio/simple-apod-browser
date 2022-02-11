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

package br.com.mathsemilio.simpleapodbrowser.common.util.converter

import br.com.mathsemilio.simpleapodbrowser.domain.model.*

fun ApodSchema.toApod(): Apod {
    return Apod(
        title = title,
        url = url,
        date = date,
        mediaType = mediaType,
        explanation = explanation,
        thumbnailUrl = thumbnailUrl,
        copyright = copyright
    )
}

fun List<ApodSchema>.toApodList(): List<Apod> {
    val apodList = mutableListOf<Apod>()

    this.forEach { apodSchema -> apodList.add(apodSchema.toApod()) }

    return apodList.toList()
}
