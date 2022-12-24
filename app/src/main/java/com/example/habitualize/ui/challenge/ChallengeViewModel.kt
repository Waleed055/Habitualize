package com.example.habitualize.ui.challenge

import android.app.Activity
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitualize.db.entities.DBChallengeDetailModel
import com.example.habitualize.db.entities.DBDailyChallengeModel
import com.example.habitualize.db.repository.MyRepository
import com.example.habitualize.ui.models.CommunityChallengeModel
import com.example.habitualize.ui.models.FeedModel
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.userId
import com.example.habitualize.utils.userName
import com.google.firebase.firestore.FirebaseFirestore
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(private val myRepository: MyRepository) : ViewModel() {
    var isTaskAdded = MutableLiveData<Boolean>()
    private var hudLoader: KProgressHUD? = null

    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var challengeData = MutableLiveData<CommunityChallengeModel>()


    fun createNewChallenge(
        activity: Activity,
        challenge_name: String,
        challenge_description: String,
        challenge_emoji: String,
        language_code: String,
        tasksList: ArrayList<String>
    ) {
        showLoader(activity)
        viewModelScope.launch {
            var dBDailyChallengeModel = DBDailyChallengeModel(
                challenge_name = challenge_name,
                challenge_description = challenge_description,
                challenge_emoji = challenge_emoji,
                langCode = language_code,
                isUserLocal = true
            )
            myRepository.insertDailyChallenge(dBDailyChallengeModel)

            for (task in tasksList) {
                var dbChallengeDetailModel = DBChallengeDetailModel(
                    challenge = task,
                    challenge_name = challenge_name,
                    langCode = language_code,
                    isOpened = false,
                    isUserLocal = true
                )
                myRepository.insertChallengeDetail(dbChallengeDetailModel) // insert challenge tasks to db
            }
            isTaskAdded.postValue(true)
            dismissLoader()
        }

    }

    fun createCommunityChallenge(activity: Activity, challenge: CommunityChallengeModel) {
        showLoader(activity)
        val id = db.collection("community_challenges").document().id
        challenge.challenge_id = id
        db.collection("community_challenges").document(id).set(challenge)
            .addOnSuccessListener {
                isTaskAdded.value = true
                Handler(Looper.myLooper()!!).postDelayed({
                    dismissLoader()
                },1000)
            }
            .addOnFailureListener {
                isTaskAdded.value = false
                dismissLoader()
            }
    }

    fun updateCommunityChallenge(activity: Activity, challenge: CommunityChallengeModel) {
        showLoader(activity)
        db.collection("community_challenges").document(challenge.challenge_id)
            .update(
                "user_id", challenge.user_id,
                "user_name", challenge.user_name,
                "challenge_name", challenge.challenge_name,
                "challenge_description", challenge.challenge_description,
                "challenge_emoji", challenge.challenge_emoji,
                "challenge_list", challenge.challenge_list,
                "language_code", challenge.language_code
            )
            .addOnSuccessListener {
                isTaskAdded.value = true
                Handler(Looper.myLooper()!!).postDelayed({
                    dismissLoader()
                },1000)
            }
            .addOnFailureListener {
                isTaskAdded.value = false
                dismissLoader()
            }
    }

    fun getChallengeData(activity: Activity, challenge_id: String) {
        showLoader(activity)
        db.collection("community_challenges")
            .document(challenge_id)
            .addSnapshotListener { snapShot, error ->
                if (snapShot == null) {
                    return@addSnapshotListener
                }

                challengeData.value = CommunityChallengeModel(
                    user_id = snapShot?.get("user_id") as String,
                    user_name = snapShot?.get("user_name") as String,
                    challenge_id = snapShot?.get("challenge_id") as String,
                    challenge_name = snapShot["challenge_name"] as String,
                    challenge_description = snapShot["challenge_description"] as String,
                    challenge_emoji = snapShot["challenge_emoji"] as String,
                    language_code = snapShot["language_code"] as String,
                    likes = snapShot["likes"] as ArrayList<String>,
                    challenge_list = snapShot["challenge_list"] as ArrayList<String>
                )
                Handler(Looper.myLooper()!!).postDelayed({
                    dismissLoader()
                },1000)

            }
    }

    //--------------------------------------------------------------------------------------------------
    private fun showLoader(activity: Activity) {
        hudLoader = KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .show();
    }

    fun dismissLoader() {
        if (hudLoader != null) {
            hudLoader?.dismiss()
        }
    }

}