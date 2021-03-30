package br.com.mathsemilio.simpleapodbrowser.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mathsemilio.simpleapodbrowser.common.CACHED_APOD_TABLE
import java.io.Serializable

@Entity(tableName = CACHED_APOD_TABLE)
data class CachedAPoD(
    val title: String,
    val url: String,
    @PrimaryKey(autoGenerate = false)
    val date: String,
    val mediaType: String,
    val explanation: String,
    val thumbnailUrl: String?,
) : Serializable