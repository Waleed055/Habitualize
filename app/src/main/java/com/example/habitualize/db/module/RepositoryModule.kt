package com.example.habitualize.db.module

import com.example.habitualize.db.dao.*
import com.example.habitualize.db.repository.MyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBloodPressureRepository(
        dailyChallengesDao: DailyChallengesDao,
        challengeDetailDao: ChallengeDetailDao,
        appTaskDao: AppTaskDao,
        completeRemindersDao: CompleteRemindersDao,
        themeDao: ThemeDao
    ) = MyRepository(dailyChallengesDao, challengeDetailDao, appTaskDao, completeRemindersDao, themeDao)
}