package com.example.habitualize.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "t_daily_challenge_record")
data class DBDailyChallengeModel(
    var challenge_name: String = "",
    var challenge_description: String = "",
    var challenge_emoji: String = "",
    var isUserLocal: Boolean = false,
    var isOpened: Boolean = false,
    var isHidden: Boolean = false,
    var langCode: String = "en"
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}