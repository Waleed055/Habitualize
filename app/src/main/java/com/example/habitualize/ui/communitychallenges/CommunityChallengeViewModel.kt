package com.example.habitualize.ui.communitychallenges

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitualize.ui.models.CommunityChallengeModel
import com.example.habitualize.ui.models.FeedCommentsModel
import com.example.habitualize.ui.models.FeedModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kaopiz.kprogresshud.KProgressHUD

class CommunityChallengeViewModel: ViewModel() {
    private var hudLoader: KProgressHUD? = null

    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    //live data
    var challengeList = MutableLiveData<ArrayList<CommunityChallengeModel>>()


    fun getCommunityChallengesByLanguageCode(activity: Activity, languageCode: String) {
        showLoader(activity)
        var mChallengeList = arrayListOf<CommunityChallengeModel>()
        db.collection("community_challenges")
            .whereEqualTo("language_code", languageCode)
            .get()
            .addOnSuccessListener {
                for (document in it.documents) {
                    mChallengeList.add(
                        CommunityChallengeModel(
                            user_id = document["user_id"] as String,
                            user_name = document["user_name"] as String,
                            challenge_id = document["challenge_id"] as String,
                            challenge_name = document["challenge_name"] as String,
                            challenge_description = document["challenge_description"] as String,
                            challenge_emoji = document["challenge_emoji"] as String,
                            language_code = document["language_code"] as String,
                            likes = document["likes"] as ArrayList<String>,
                            challenge_list = document["challenge_list"] as ArrayList<String>
                        )
                    )
                }
                challengeList.value = mChallengeList
                dismissLoader()
            }
            .addOnFailureListener {
                challengeList.value = mChallengeList
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