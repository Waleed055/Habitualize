package com.example.habitualize.ui.notification

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitualize.ui.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kaopiz.kprogresshud.KProgressHUD

class NotificationViewModel: ViewModel()  {

    private var hudLoader: KProgressHUD? = null
    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    // live data
    var notificationList = MutableLiveData<ArrayList<NotificationModel>>()


    fun getNotifications(activity: Activity, user_name: String) {
        println("checking the user name.......... $user_name")
        showLoader(activity)
        db.collection("users")
            .document(user_name)
            .addSnapshotListener { snapshot, e ->
                dismissLoader()
                if (snapshot == null) {
                    return@addSnapshotListener
                }
                var list = arrayListOf<NotificationModel>()
                if(snapshot?.data?.get("notifications_time") != null) {
                    val mNotificationList =
                        snapshot?.data?.get("notifications_time") as ArrayList<Map<String, NotificationModel>>
                    mNotificationList.forEach { notification ->
                        list.add(
                            NotificationModel(
                                time = notification["time"] as String,
                                title = notification["title"] as String,
                                body = notification["body"] as String
                            )
                        )
                    }
                }
                notificationList.postValue(list)
            }
    }

    fun updateNotifications(activity: Activity, user_name: String, list: ArrayList<NotificationModel>) {
        showLoader(activity)
        db.collection("users")
            .document(user_name)
            .update("notifications_time", list)
            .addOnSuccessListener { dismissLoader() }
            .addOnFailureListener { dismissLoader() }
    }

    //--------------------------------------------------------------------------------------------------
    fun showLoader(activity: Activity) {
        hudLoader = KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .show()
    }

    fun dismissLoader() {
        if (hudLoader != null) {
            hudLoader?.dismiss()
        }
    }

}