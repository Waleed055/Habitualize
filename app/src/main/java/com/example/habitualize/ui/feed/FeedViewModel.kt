package com.example.habitualize.ui.feed

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitualize.ui.models.FeedCommentsModel
import com.example.habitualize.ui.models.FeedModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kaopiz.kprogresshud.KProgressHUD

class FeedViewModel : ViewModel() {
    private var hudLoader: KProgressHUD? = null

    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    //live data
    var feedList = MutableLiveData<ArrayList<FeedModel>>()


    fun getAllFeedsByLanguageCode(activity: Activity, languageCode: String) {
        showLoader(activity)
        var mFeedList = arrayListOf<FeedModel>()
        db.collection("feeds")
            .whereEqualTo("language_code", languageCode)
            .get()
            .addOnSuccessListener {
                for (document in it.documents) {
                    mFeedList.add(
                        FeedModel(
                            feed_id = document["feed_id"] as String,
                            user_image = document["user_image"] as String,
                            user_id = document["user_id"] as String,
                            user_name = document["user_name"] as String,
                            question = document["question"] as String,
                            answer = document["answer"] as String,
                            likes = document["likes"] as ArrayList<String>,
                            flowers_counter = document["flowers_counter"] as String,
                            hug_counter = document["hug_counter"] as String,
                            high_five_counter = document["high_five_counter"] as String,
                            love_counter = document["love_counter"] as String,
                            appreciation_counter = document["appreciation_counter"] as String,
                            thank_you_counter = document["thank_you_counter"] as String,
                            reported = document["reported"] as Boolean,
                            created_at = document["created_at"] as String,
                            language_code = document["language_code"] as String,
                            comments_list = if (document["comments_list"] is ArrayList<*>) {
                                document["comments_list"] as ArrayList<FeedCommentsModel>
                            }else{
                                arrayListOf<FeedCommentsModel>()
                            }
                        )
                    )
                }
                feedList.value = mFeedList
                dismissLoader()
            }
            .addOnFailureListener {
                feedList.value = mFeedList
                dismissLoader()
            }
    }

    fun deleteFeed(activity: Activity, feedId: String){
        showLoader(activity)
        db.collection("feeds")
            .document(feedId)
            .delete()
            .addOnSuccessListener {
                println("deleted..........success")
                dismissLoader()
            }
            .addOnFailureListener {
                println("deleted..........failed")
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