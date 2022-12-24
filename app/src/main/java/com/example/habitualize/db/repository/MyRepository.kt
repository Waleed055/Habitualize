package com.example.habitualize.db.repository

import androidx.lifecycle.LiveData
import com.example.habitualize.db.dao.*
import com.example.habitualize.db.entities.*
import javax.inject.Inject

class MyRepository @Inject constructor(
    private var dailyChallengesDao: DailyChallengesDao,
    private var challengeDetailDao: ChallengeDetailDao,
    private var appTaskDao: AppTaskDao,
    private var completeRemindersDao: CompleteRemindersDao,
    private var themeDao: ThemeDao
) {

    suspend fun insertDailyChallenge(dbDailyChallengeModel: DBDailyChallengeModel) {
        dailyChallengesDao.insert(dbDailyChallengeModel)
    }

    suspend fun insertChallengeDetail(dbChallengeDetailModel: DBChallengeDetailModel) {
        challengeDetailDao.insert(dbChallengeDetailModel)
    }

    fun getChallengesList(userLang: String): List<DBDailyChallengeModel> {
        return dailyChallengesDao.getChallengesList(userLang)
    }

    fun getChallengesLiveList(userLang: String): LiveData<List<DBDailyChallengeModel>> {
        return dailyChallengesDao.getChallengesLiveList(userLang)
    }

    fun getHiddenChallengesLiveList(userLang: String): LiveData<List<DBDailyChallengeModel>> {
        return dailyChallengesDao.getHiddenChallengesLiveList(userLang)
    }

    fun getChallengeByName(challenge_name: String, userLang: String): DBDailyChallengeModel {
        return dailyChallengesDao.getChallengeByName(challenge_name, userLang)
    }

    suspend fun openChallenge(challenge_name: String, userLang: String) {
        return dailyChallengesDao.update(challenge_name, userLang)
    }


    fun getChallengeDetailListByChallengeName(
        challenge_name: String,
        userLang: String
    ): List<DBChallengeDetailModel> {
        return challengeDetailDao.getChallengeDetailListByChallengeName(challenge_name, userLang)
    }

    fun getChallengeDetailLiveListByChallengeName(
        challenge_name: String,
        userLang: String
    ): LiveData<List<DBChallengeDetailModel>> {
        return challengeDetailDao.getChallengeDetailLiveListByChallengeName(
            challenge_name,
            userLang
        )
    }


    suspend fun updateTaskDetail(id: Long, openDate: Long, userLang: String) {
        return challengeDetailDao.update(id, openDate, userLang)
    }

    suspend fun restartTask(challenge_name: String, userLang: String) {
        return challengeDetailDao.restartTask(challenge_name, userLang)
    }

    suspend fun hideChallenge(challenge_name: String, userLang: String) {
        return dailyChallengesDao.hideChallenge(challenge_name, userLang)
    }

    suspend fun unHideChallenge(challenge_name: String, userLang: String) {
        return dailyChallengesDao.unHideChallenge(challenge_name, userLang)
    }

//--------------------------------------------------------------------------------------------------

    suspend fun insertAppTasks(dbAppTasksModel: DBAppTasksModel) {
        appTaskDao.insert(dbAppTasksModel)
    }

    fun getAppTasksList(): LiveData<List<DBAppTasksModel>> {
        return appTaskDao.getAppTasksList()
    }

    suspend fun completeTask(id: Long) {
        appTaskDao.completeTask(id)
    }

    fun deleteAppTask() {
        appTaskDao.deleteAllData()
    }

    //----------------------------------------------------------------------------------------------
    suspend fun deleteAllData() {
        appTaskDao.deleteAllData()
        challengeDetailDao.deleteAllData()
        challengeDetailDao.closeAllChallenges()
        dailyChallengesDao.deleteAllData()
        dailyChallengesDao.closeAllChallenges()
        completeRemindersDao.deleteAllData()
    }

//--------------------------------------------------------------------------------------------------

    suspend fun insertCompletedReminder(dbCompleteReminderModel: DBCompleteReminderModel) {
        completeRemindersDao.insert(dbCompleteReminderModel)
    }

    suspend fun deleteCompletedReminder(dbCompleteReminderModel: DBCompleteReminderModel) {
        completeRemindersDao.delete(dbCompleteReminderModel)
    }

    fun getCompletedReminderList(): LiveData<List<DBCompleteReminderModel>> {
        return completeRemindersDao.getCompletedReminders()
    }

//--------------------------------------------------------------------------------------------------

    suspend fun purchaseTheme(dbThemeModel: DBThemeModel) {
        themeDao.insert(dbThemeModel)
    }

    fun getPurchasedThemes(): LiveData<List<DBThemeModel>> {
        return themeDao.getPurchasedThemes()
    }

}