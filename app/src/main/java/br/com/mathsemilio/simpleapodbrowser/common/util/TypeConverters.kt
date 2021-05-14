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
package br.com.mathsemilio.simpleapodbrowser.common.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.model.ApodSchema
import java.io.ByteArrayOutputStream

fun ApodSchema.toApod(): Apod {
    return Apod(
        title = this.title,
        url = this.url,
        date = this.date,
        mediaType = this.mediaType,
        explanation = this.explanation,
        thumbnailUrl = this.thumbnailUrl
    )
}

fun List<ApodSchema>.toApodList(): List<Apod> {
    val apodList = mutableListOf<Apod>()

    this.forEach { apodSchema -> apodList.add(apodSchema.toApod()) }

    return apodList.toList()
}

fun Bitmap.toByteArray(): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream)
    return byteArrayOutputStream.toByteArray()
}

fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}