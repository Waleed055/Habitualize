package com.example.habitualize.db.module

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.habitualize.db.AppDatabase
import com.example.habitualize.db.entities.DBChallengeDetailModel
import com.example.habitualize.db.entities.DBDailyChallengeModel
import com.example.habitualize.utils.languageCodeList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DB_NAME = "Habitualizer.db"
    private lateinit var instance: AppDatabase

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        instance = Room.databaseBuilder(appContext, AppDatabase::class.java, DB_NAME)
            .allowMainThreadQueries()
            .addMigrations()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    GlobalScope.launch {
                        /** inserting tasks to the database */
                        insertDataToDbFromAsset(appContext, instance)
                    }
                }
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                }
            })
            .build()
        return instance
    }

    @Provides
    @Singleton
    fun provideDailyChallengeDao(appDatabase: AppDatabase) = appDatabase.dailyChallengeDao()

    @Provides
    @Singleton
    fun provideChallengeDetailDao(appDatabase: AppDatabase) = appDatabase.challengeDetailDao()

    @Provides
    @Singleton
    fun provideAppTaskDao(appDatabase: AppDatabase) = appDatabase.appTaskDao()

    @Provides
    @Singleton
    fun provideCompleteReminderDao(appDatabase: AppDatabase) = appDatabase.completeRemindersDao()

    @Provides
    @Singleton
    fun provideThemeDao(appDatabase: AppDatabase) = appDatabase.themeDao()

    // this function called only for the first time or when db cleared
    suspend fun insertDataToDbFromAsset(appContext: Context, database: AppDatabase) {
            for (languageCode in languageCodeList) {
                val file_name = "challenges_${languageCode}.json"
                val bufferReader = appContext?.assets?.open(file_name)?.bufferedReader()
                val data = bufferReader.use {
                    it?.readText()
                }
                val obj = JSONObject(data)
                val m_jArry: JSONArray = obj.getJSONArray("all_challenges")
                for (index in 0 until m_jArry.length()) {
                    val jo_inside = m_jArry.getJSONObject(index)
                    val json_challengeList = jo_inside.getJSONArray("challenges")

                    var dBDailyChallengeModel = DBDailyChallengeModel(
                        challenge_name = jo_inside.getString("challenge_name"),
                        challenge_description = jo_inside.getString("challenge_description"),
                        challenge_emoji = jo_inside.getString("emoji"),
                        langCode = languageCode
                    )
                    database.dailyChallengeDao().insert(dBDailyChallengeModel) // insert challenge object to db
                    for (i in 0 until json_challengeList.length()) {
                        var dbChallengeDetailModel = DBChallengeDetailModel(
                            challenge = json_challengeList[i].toString(),
                            challenge_name = jo_inside.getString("challenge_name"),
                            isOpened = false,
                            langCode = languageCode
                        )
                        database.challengeDetailDao().insert(dbChallengeDetailModel) // insert challenge tasks to db
                    }
                }
            }
    }

}