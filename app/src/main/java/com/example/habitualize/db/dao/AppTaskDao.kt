package com.example.habitualize.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habitualize.db.entities.DBAppTasksModel

@Dao
interface AppTaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg dbAppTasksModel: DBAppTasksModel)

    @Query("SELECT * FROM t_app_task_record")
    fun getAppTasksList(): LiveData<List<DBAppTasksModel>>

    @Query("UPDATE t_app_task_record SET isCompleted = 1 WHERE  id =:id")
    suspend fun completeTask(id: Long)

    @Delete
    suspend fun delete(vararg dbAppTasksModel: DBAppTasksModel)

    @Query("DELETE FROM t_app_task_record")
    fun deleteAllData()
}