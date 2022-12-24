package com.example.habitualize.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habitualize.db.entities.DBDailyChallengeModel

@Dao
interface DailyChallengesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg dBDailyChallengeModel: DBDailyChallengeModel)

    @Query("SELECT * FROM t_daily_challenge_record WHERE langCode = :userLang AND isHidden = 0")
    fun getChallengesList(userLang: String): List<DBDailyChallengeModel>

    @Query("SELECT * FROM t_daily_challenge_record WHERE langCode = :userLang AND isHidden = 0")
    fun getChallengesLiveList(userLang: String): LiveData<List<DBDailyChallengeModel>>

    @Query("SELECT * FROM t_daily_challenge_record WHERE langCode = :userLang AND isHidden = 1")
    fun getHiddenChallengesLiveList(userLang: String): LiveData<List<DBDailyChallengeModel>>

    @Query("SELECT * FROM t_daily_challenge_record WHERE  challenge_name =:challenge_name AND langCode = :userLang")
    fun getChallengeByName(challenge_name: String, userLang: String): DBDailyChallengeModel

    @Query("UPDATE t_daily_challenge_record SET isHidden = 1 WHERE  challenge_name =:challenge_name AND langCode = :userLang")
    suspend fun hideChallenge(challenge_name: String, userLang: String)

    @Query("UPDATE t_daily_challenge_record SET isHidden = 0 WHERE  challenge_name =:challenge_name AND langCode = :userLang")
    suspend fun unHideChallenge(challenge_name: String, userLang: String)

    @Query("UPDATE t_daily_challenge_record SET isOpened = 1 WHERE  challenge_name =:challenge_name AND langCode = :userLang")
    suspend fun update(challenge_name: String, userLang: String)

    @Delete
    suspend fun delete(vararg dBDailyChallengeModel: DBDailyChallengeModel)

    @Query("DELETE FROM t_daily_challenge_record WHERE  isUserLocal = 1")
    fun deleteAllData()

    @Query("UPDATE t_daily_challenge_record SET isOpened = 0")
    suspend fun closeAllChallenges()

}