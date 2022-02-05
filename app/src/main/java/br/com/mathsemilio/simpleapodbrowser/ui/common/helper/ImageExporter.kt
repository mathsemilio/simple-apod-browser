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

import android.net.Uri
import android.os.Build
import android.content.*
import android.graphics.Bitmap
import android.provider.MediaStore
import br.com.mathsemilio.simpleapodbrowser.common.*
import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable

class ImageExporter(
    private val context: Context
) : BaseObservable<ImageExporter.Listener>() {

    interface Listener {
        fun onApodImageExportedSuccessfully()

        fun onExportApodImageFailed()
    }

    private lateinit var contentResolver: ContentResolver

    private val imageFileName
        get() = "${System.currentTimeMillis()}.png"

    private val imageContentUri: Uri
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            else
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

    fun export(image: Bitmap) {
        try {
            val imageUri = insertImageInCollection()

            compress(image, imageUri ?: throw NullPointerException(NULL_IMAGE_URI))

            notify { listener -> listener.onApodImageExportedSuccessfully() }
        } catch (exception: Exception) {
            notify { listener -> listener.onExportApodImageFailed() }
        }
    }

    private fun insertImageInCollection(): Uri? {
        val contentValues = ContentValues()
        contentResolver = context.contentResolver

        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)

        return contentResolver.insert(imageContentUri, contentValues)
    }

    private fun compress(image: Bitmap, uri: Uri) {
        val outputStream = contentResolver.openOutputStream(
            uri
        ) ?: throw RuntimeException(COULD_NOT_OPEN_OUTPUT_STREAM)

        image.compress(Bitmap.CompressFormat.PNG, 0, outputStream)

        outputStream.close()
    }
}
