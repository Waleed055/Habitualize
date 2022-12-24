package com.example.habitualize.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "t_challenge_detail_record")
data class DBChallengeDetailModel(
    var challenge: String = "",
    var challenge_name: String = "",
    var openDate: Long = 0,
    var isOpened: Boolean = false,
    var isUserLocal: Boolean = false,
    var langCode: String = "en"
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}
