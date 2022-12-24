package com.example.habitualize.ui.hidentask.hiddentaskdetail

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitualize.db.entities.DBDailyChallengeModel
import com.example.habitualize.db.repository.MyRepository
import com.example.habitualize.ui.home.taskdetail.TaskDetailActivity
import com.example.habitualize.ui.models.ChallengeDetailModel
import com.example.habitualize.ui.notification.NotificationModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiddenTaskDetailViewModel @Inject constructor(val myRepository: MyRepository) : ViewModel()  {
    var tasksList = MutableLiveData<ArrayList<ChallengeDetailModel>>()
    var challengeData = MutableLiveData<DBDailyChallengeModel>()
    var mTasksList = ArrayList<ChallengeDetailModel>()
    private var hudLoader: KProgressHUD? = null
    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    // live data
    var notificationList = MutableLiveData<ArrayList<NotificationModel>>()

    fun getTasks(context: Context, challenge_name: String, userLang: String) {

        myRepository.getChallengeDetailLiveListByChallengeName(challenge_name, userLang).observe(context as HiddenTaskDetailActivity){
            mTasksList.clear()
            for (item in it) {
                mTasksList.add(
                    ChallengeDetailModel(
                        id = item.id,
                        isOpened = item.isOpened,
                        challenge = item.challenge,
                        challenge_name = item.challenge_name,
                        openDate = item.openDate
                    )
                )
            }
            tasksList.value = mTasksList
        }
    }

    fun getChallengeByName(challenge_name: String, userLang: String){
        challengeData.value = myRepository.getChallengeByName(challenge_name, userLang)
    }

    fun restartChallenge(challenge_name: String, userLang: String){
        viewModelScope.launch {
            myRepository.restartTask(challenge_name, userLang)
        }
    }

    fun hideChallenge(challenge_name: String, userLang: String){
        viewModelScope.launch {
            myRepository.hideChallenge(challenge_name, userLang)
        }
    }

    fun unHideChallenge(challenge_name: String, userLang: String){
        viewModelScope.launch {
            myRepository.unHideChallenge(challenge_name, userLang)
        }
    }

    fun getNotifications(activity: Activity, user_name: String) {
        showLoader(activity)
        db.collection("users")
            .document(user_name)
            .addSnapshotListener { snapshot, e ->
                dismissLoader()
                if (snapshot == null) {
                    return@addSnapshotListener
                }
                var list = arrayListOf<NotificationModel>()
                val mNotificationList = snapshot?.data?.get("notifications_time") as ArrayList<Map<String, NotificationModel>>
                mNotificationList.forEach { notification ->
                    list.add(
                        NotificationModel(
                            time = notification["time"] as String,
                            title = notification["title"] as String,
                            body = notification["body"] as String
                        )
                    )
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