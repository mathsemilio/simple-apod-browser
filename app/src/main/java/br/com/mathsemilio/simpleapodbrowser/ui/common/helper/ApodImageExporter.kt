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
import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import java.util.*

class ApodImageExporter(private val context: Context) : BaseObservable<ApodImageExporter.Listener>() {

    interface Listener {
        fun onApodImageExportedSuccessfully()

        fun onExportApodImageFailed()
    }

    fun export(apodImage: Bitmap) {
        val resolver = context.contentResolver

        try {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, getAPoDImageName())
            }

            val apodImageUri = resolver.insert(getImageCollectionUri(), contentValues)

            compressImage(resolver, apodImage, apodImageUri!!)

            listeners.forEach { listener ->
                listener.onApodImageExportedSuccessfully()
            }
        } catch (e: Exception) {
            listeners.forEach { listener ->
                listener.onExportApodImageFailed()
            }
        }
    }

    private fun getAPoDImageName(): String {
        return "APoD_${System.currentTimeMillis()}.png"
    }

    private fun getImageCollectionUri(): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )
        else
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    private fun compressImage(resolver: ContentResolver, apodImage: Bitmap, apodImageUri: Uri) {
        val outputStream = resolver.openOutputStream(apodImageUri)!!

        apodImage.compress(Bitmap.CompressFormat.PNG, 0, outputStream)

        Objects.requireNonNull(outputStream)

        outputStream.close()
    }
}