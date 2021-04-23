package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import java.io.OutputStream
import java.util.*

class APoDImageExporter(private val context: Context) :
    BaseObservable<APoDImageExporter.Listener>() {

    interface Listener {
        fun onAPoDImageExportedSuccessfully()
        fun onErrorExportingAPoDImage()
    }

    fun export(apodImage: Bitmap) {
        val outputStream: OutputStream
        val resolver = context.contentResolver

        try {
            val imageCollection =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Images.Media.getContentUri(
                        MediaStore.VOLUME_EXTERNAL_PRIMARY
                    )
                } else {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, getAPoDImageName())
            }

            val apodImageUri = resolver.insert(imageCollection, contentValues)

            outputStream = resolver.openOutputStream(apodImageUri!!)!!

            apodImage.compress(Bitmap.CompressFormat.PNG, 0, outputStream)

            Objects.requireNonNull(outputStream)

            listeners.forEach { listener ->
                listener.onAPoDImageExportedSuccessfully()
            }
        } catch (e: Exception) {
            listeners.forEach { listener ->
                listener.onErrorExportingAPoDImage()
            }
            e.printStackTrace()
        }
    }

    private fun getAPoDImageName(): String {
        return "APoD_${System.currentTimeMillis()}.png"
    }
}