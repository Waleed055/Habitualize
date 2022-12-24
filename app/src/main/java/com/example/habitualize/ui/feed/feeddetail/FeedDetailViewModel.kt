package com.example.habitualize.ui.feed.feeddetail

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitualize.ui.models.FeedCommentsModel
import com.example.habitualize.ui.models.FeedModel
import com.example.habitualize.ui.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kaopiz.kprogresshud.KProgressHUD

class FeedDetailViewModel : ViewModel() {
    private var hudLoader: KProgressHUD? = null

    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    //live data
    var feedModel = MutableLiveData<FeedModel>()
    var commentsList = ArrayList<FeedCommentsModel>()

    fun getFeedDetail(activity: Activity, feedId: String) {
        showLoader(activity)
        db.collection("feeds")
            .document(feedId)
            .addSnapshotListener { snapshot, e ->
                if (snapshot == null) {
                    return@addSnapshotListener
                }
                commentsList.clear()

                val mCommentsList: ArrayList<Map<String, FeedCommentsModel>> =
                    snapshot?.data?.get("comments_list") as ArrayList<Map<String, FeedCommentsModel>>

                mCommentsList.forEach { comment ->
                    commentsList.add(
                        FeedCommentsModel(
                            user_id = comment["user_id"] as String,
                            user_name = comment["user_name"] as String,
                            user_image = comment["user_image"] as String,
                            created_at = comment["created_at"] as String,
                            comment = comment["comment"] as String
                        )
                    )
                }

                var mFeedModel = FeedModel(
                    feed_id = snapshot?.get("feed_id") as String,
                    user_image = snapshot["user_image"] as String,
                    user_id = snapshot["user_id"] as String,
                    user_name = snapshot["user_name"] as String,
                    question = snapshot["question"] as String,
                    answer = snapshot["answer"] as String,
                    likes = snapshot["likes"] as ArrayList<String>,
                    flowers_counter = snapshot["flowers_counter"] as String,
                    hug_counter = snapshot["hug_counter"] as String,
                    high_five_counter = snapshot["high_five_counter"] as String,
                    love_counter = snapshot["love_counter"] as String,
                    appreciation_counter = snapshot["appreciation_counter"] as String,
                    thank_you_counter = snapshot["thank_you_counter"] as String,
                    reported = snapshot["reported"] as Boolean,
                    created_at = snapshot["created_at"] as String,
                    language_code = snapshot["language_code"] as String,
                    comments_list = commentsList
                )
                feedModel.value = mFeedModel
                dismissLoader()
            }
    }

    fun createComment(activity: Activity, feedId: String, comment: FeedCommentsModel) {
        commentsList.add(comment)
        showLoader(activity)
        db.collection("feeds")
            .document(feedId)
            .update("comments_list", commentsList)
            .addOnSuccessListener {
                dismissLoader()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show()
                dismissLoader()
            }
    }

    fun sendGift(activity: Activity, feed: FeedModel, giftIndex: Int){
        var counter = 0
        var gift = when(giftIndex){
            0-> {
                counter = feed.flowers_counter.toInt() + 1
                "flowers_counter"
            }
            1-> {
                counter = feed.hug_counter.toInt() + 1
                "hug_counter"
            }
            2-> {
                counter = feed.high_five_counter.toInt() + 1
                "high_five_counter"
            }
            3-> {
                counter = feed.love_counter.toInt() + 1
                "love_counter"
            }
            4-> {
                counter = feed.appreciation_counter.toInt() + 1
                "appreciation_counter"
            }
            5-> {
                counter = feed.thank_you_counter.toInt() + 1
                "thank_you_counter"
            }
            else-> {
                counter = feed.flowers_counter.toInt() + 1
                "flowers_counter"
            }
        }
        showLoader(activity)
        db.collection("feeds")
            .document(feed.feed_id)
            .update(gift, "$counter")
            .addOnSuccessListener {
                dismissLoader()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show()
                dismissLoader()
            }
    }


    fun hitLike(activity: Activity, feed: FeedModel, userName: String){
        showLoader(activity)
        var likeList = feed.likes
        likeList.add(userName)
        db.collection("feeds")
            .document(feed.feed_id)
            .update("likes", likeList)
            .addOnSuccessListener {
                dismissLoader()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show()
                dismissLoader()
            }
    }

    fun deleteComment(activity: Activity, feedId: String, commentList: ArrayList<FeedCommentsModel>){
        showLoader(activity)
        db.collection("feeds")
            .document(feedId)
            .update("comments_list", commentList)
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