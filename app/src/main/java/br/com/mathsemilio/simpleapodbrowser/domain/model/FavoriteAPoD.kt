package br.com.mathsemilio.simpleapodbrowser.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mathsemilio.simpleapodbrowser.common.FAVORITE_APoDS_TABLE
import java.io.Serializable

@Entity(tableName = FAVORITE_APoDS_TABLE)
data class FavoriteAPoD(
    val title: String,
    @PrimaryKey(autoGenerate = false)
    val date: String,
    val url: String,
    val mediaType: String,
    val explanation: String,
    val thumbnailUrl: String?,
    val copyright: String?
) : Serializable