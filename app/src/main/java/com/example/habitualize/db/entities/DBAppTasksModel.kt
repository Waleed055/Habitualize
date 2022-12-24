package com.example.habitualize.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "t_app_task_record")
data class DBAppTasksModel(
    var reward: Int = 0,
    var target: Int = 0,
    var reward_text: String = "",
    var type: String = "",
    var isCompleted: Boolean = false
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}