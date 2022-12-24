package com.example.habitualize.ui.feed.createfeed

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitualize.ui.models.FeedCommentsModel
import com.example.habitualize.ui.models.FeedModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kaopiz.kprogresshud.KProgressHUD

class CreateFeedViewModel: ViewModel() {
    private var hudLoader: KProgressHUD? = null

    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isFeedUploaded = MutableLiveData<Boolean>()


    fun createFeed(activity: Activity, feedModel: FeedModel){
        showLoader(activity)
        val id = db.collection("feeds").document().id
        feedModel.feed_id = id
        db.collection("feeds").document(id).set(feedModel)
            .addOnSuccessListener {
                isFeedUploaded.value = true
                dismissLoader()
            }
            .addOnFailureListener {
                isFeedUploaded.value = false
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