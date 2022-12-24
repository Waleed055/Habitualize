package com.example.habitualize.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habitualize.db.entities.DBChallengeDetailModel

@Dao
interface ChallengeDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg dBChallengeDetailModel: DBChallengeDetailModel)

    @Query("SELECT * FROM t_challenge_detail_record WHERE challenge_name = :challenge_name AND langCode = :userLang")
    fun getChallengeDetailListByChallengeName(challenge_name: String, userLang: String): List<DBChallengeDetailModel>

    @Query("SELECT * FROM t_challenge_detail_record WHERE challenge_name = :challenge_name AND langCode = :userLang")
    fun getChallengeDetailLiveListByChallengeName(challenge_name: String, userLang: String): LiveData<List<DBChallengeDetailModel>>

    @Query("UPDATE t_challenge_detail_record SET isOpened = 1, openDate = :openDate WHERE  id =:id AND langCode = :userLang")
    suspend fun update(id: Long, openDate: Long, userLang: String)

    @Query("UPDATE t_challenge_detail_record SET isOpened = 0 WHERE  challenge_name =:challenge_name AND langCode = :userLang")
    suspend fun restartTask(challenge_name: String, userLang: String)

    @Delete
    suspend fun delete(vararg dBDailyChallengeModel: DBChallengeDetailModel)

    @Query("DELETE FROM t_challenge_detail_record WHERE  isUserLocal = 1")
    fun deleteAllData()

    @Query("UPDATE t_challenge_detail_record SET isOpened = 0")
    suspend fun closeAllChallenges()
}