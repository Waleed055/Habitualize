package com.example.habitualize.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habitualize.db.dao.*
import com.example.habitualize.db.entities.*

@Database(
    entities = [
        DBDailyChallengeModel::class,
        DBChallengeDetailModel::class,
        DBAppTasksModel::class,
        DBCompleteReminderModel::class,
        DBThemeModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyChallengeDao(): DailyChallengesDao
    abstract fun challengeDetailDao(): ChallengeDetailDao
    abstract fun appTaskDao(): AppTaskDao
    abstract fun completeRemindersDao(): CompleteRemindersDao
    abstract fun themeDao(): ThemeDao
}