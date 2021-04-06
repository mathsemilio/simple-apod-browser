package br.com.mathsemilio.simpleapodbrowser.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoDSchema
import java.io.ByteArrayOutputStream

fun APoDSchema.toAPoD() = APoD(
    this.title, this.url, this.date, this.mediaType, this.explanation, this.thumbnailUrl
)

fun List<APoDSchema>.toAPoDList(): List<APoD> {
    val apodList = mutableListOf<APoD>()
    this.forEach { aPoDSchema ->
        apodList.add(aPoDSchema.toAPoD())
    }
    return apodList.toList()
}

fun Bitmap.toByteArray(): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    return byteArrayOutputStream.toByteArray()
}

fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}