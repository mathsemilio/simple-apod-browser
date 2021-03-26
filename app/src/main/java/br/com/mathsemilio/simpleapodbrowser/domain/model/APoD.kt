package br.com.mathsemilio.simpleapodbrowser.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mathsemilio.simpleapodbrowser.common.FAVORITE_APOD_TABLE
import java.io.Serializable

@Entity(tableName = FAVORITE_APOD_TABLE)
data class APoD(
    val title: String,
    val url: String,
    @PrimaryKey(autoGenerate = false)
    val date: String,
    val mediaType: String,
    val explanation: String,
    val thumbnailUrl: String?,
) : Serializable