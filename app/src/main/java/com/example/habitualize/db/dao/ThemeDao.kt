package com.example.habitualize.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.habitualize.db.entities.DBDailyChallengeModel
import com.example.habitualize.db.entities.DBThemeModel

@Dao
interface ThemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg dbThemeModel: DBThemeModel)

    @Query("SELECT * FROM t_theme_record")
    fun getPurchasedThemes(): LiveData<List<DBThemeModel>>

    @Query("DELETE FROM t_theme_record")
    fun deleteAllData()
}