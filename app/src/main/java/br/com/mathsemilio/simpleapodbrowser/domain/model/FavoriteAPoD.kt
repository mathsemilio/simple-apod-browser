package br.com.mathsemilio.simpleapodbrowser.domain.model

import androidx.room.Entity
import br.com.mathsemilio.simpleapodbrowser.common.FAVORITE_APoDS_TABLE

@Entity(tableName = FAVORITE_APoDS_TABLE)
data class FavoriteAPoD(
    val title: String,
    val date: String,
    val url: String,
    val mediaType: String,
    val explanation: String,
    val thumbnailUrl: String?
)