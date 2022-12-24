package com.example.habitualize.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habitualize.db.entities.DBChallengeDetailModel
import com.example.habitualize.db.entities.DBCompleteReminderModel

@Dao
interface CompleteRemindersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg dbCompleteReminderModel: DBCompleteReminderModel)

    @Query("SELECT * FROM t_completed_reminder_record")
    fun getCompletedReminders(): LiveData<List<DBCompleteReminderModel>>

    @Delete
    suspend fun delete(vararg dbCompleteReminderModel: DBCompleteReminderModel)

    @Query("DELETE FROM t_completed_reminder_record")
    fun deleteAllData()
}