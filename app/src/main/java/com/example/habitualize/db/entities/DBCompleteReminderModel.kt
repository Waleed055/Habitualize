package com.example.habitualize.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "t_completed_reminder_record")
data class DBCompleteReminderModel(
    var reminder: String = "",
    var language_code: String = ""
): Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}