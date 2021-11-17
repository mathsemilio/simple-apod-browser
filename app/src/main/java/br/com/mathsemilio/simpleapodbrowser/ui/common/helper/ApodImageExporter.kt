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

package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import br.com.mathsemilio.simpleapodbrowser.common.COULD_NOT_OPEN_OUTPUT_STREAM_EXCEPTION
import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import java.util.*

class ApodImageExporter(
    private val context: Context
) : BaseObservable<ApodImageExporter.Listener>() {

    interface Listener {
        fun onApodImageExportedSuccessfully()

        fun onExportApodImageFailed()
    }

    private val exportedApodImageFileNameFormat
        get() = "APOD_${System.currentTimeMillis()}.png"

    private val imageCollectionUri: Uri
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            else
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

    fun export(apodImage: Bitmap) {
        val contentResolver = context.contentResolver

        try {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, exportedApodImageFileNameFormat)
            }

            contentResolver.insert(imageCollectionUri, contentValues)?.let { imageUri ->
                compressImage(contentResolver, apodImage, imageUri)
            }

            notify { listener ->
                listener.onApodImageExportedSuccessfully()
            }
        } catch (exception: Exception) {
            notify { listener ->
                listener.onExportApodImageFailed()
            }
        }
    }

    private fun compressImage(resolver: ContentResolver, image: Bitmap, imageUri: Uri) {
        val outputStream = resolver.openOutputStream(
            imageUri
        ) ?: throw IllegalStateException(COULD_NOT_OPEN_OUTPUT_STREAM_EXCEPTION)

        image.compress(Bitmap.CompressFormat.PNG, 0, outputStream)

        Objects.requireNonNull(outputStream)

        outputStream.close()
    }
}
