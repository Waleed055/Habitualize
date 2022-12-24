package com.example.habitualize.ui.communitychallenges.communitychallengedetail

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitualize.db.entities.DBChallengeDetailModel
import com.example.habitualize.db.entities.DBDailyChallengeModel
import com.example.habitualize.db.repository.MyRepository
import com.example.habitualize.ui.models.CommunityChallengeModel
import com.example.habitualize.ui.models.FeedModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityChallengeDetailViewModel @Inject constructor(val myRepository: MyRepository) : ViewModel() {
    private var hudLoader: KProgressHUD? = null

    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    //live data
    var challengeData = MutableLiveData<CommunityChallengeModel>()
    var isChallengeAdded = MutableLiveData<Boolean>()


    fun getChallengeData(activity: Activity, challenge_id: String) {
        showLoader(activity)
        db.collection("community_challenges")
            .document(challenge_id)
            .addSnapshotListener { snapShot, error ->
                dismissLoader()
                if (snapShot == null){
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
            }
    }

    fun addChallengeToDB(
        activity: Activity,
        challenge_name: String,
        challenge_description: String,
        challenge_emoji : String,
        language_code: String,
        tasksList: ArrayList<String>
    ){
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

            for(task in tasksList){
                var dbChallengeDetailModel = DBChallengeDetailModel(
                    challenge = task,
                    challenge_name = challenge_name,
                    langCode = language_code,
                    isOpened = false
                )
                myRepository.insertChallengeDetail(dbChallengeDetailModel) // insert challenge tasks to db
            }
            isChallengeAdded.postValue(true)
            dismissLoader()
        }

    }

    fun hitLike(activity: Activity, challenge: CommunityChallengeModel, userName: String){
        showLoader(activity)
        var likeList = challenge.likes
        likeList.add(userName)
        db.collection("community_challenges")
            .document(challenge.challenge_id)
            .update("likes", likeList)
            .addOnSuccessListener {
                dismissLoader()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show()
                dismissLoader()
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

    private fun dismissLoader() {
        if (hudLoader != null) {
            hudLoader?.dismiss()
        }
    }
}