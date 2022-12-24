package com.example.habitualize.ui.home.viewmodels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitualize.R
import com.example.habitualize.db.entities.DBChallengeDetailModel
import com.example.habitualize.db.entities.DBDailyChallengeModel
import com.example.habitualize.db.repository.MyRepository
import com.example.habitualize.ui.home.HomeActivity
import com.example.habitualize.ui.models.BlogModel
import com.example.habitualize.ui.models.ChallengeDetailModel
import com.example.habitualize.ui.models.DailyChallengesModel
import com.example.habitualize.utils.languageCodeList
import com.google.firebase.firestore.FirebaseFirestore
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val myRepository: MyRepository) : ViewModel() {

    var challengesList = MutableLiveData<ArrayList<DailyChallengesModel>>()
    private var hudLoader: KProgressHUD? = null
    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    // live data
    var daily_quote = MutableLiveData<String>()
    var relaxing_music_url = MutableLiveData<String>()
    var blog_model = MutableLiveData<BlogModel>()

    fun getChallengesFromDb(activity: Activity, userLang : String) {
        var dailyChallengesList = arrayListOf<DailyChallengesModel>()
        var challengeDetailList = arrayListOf<ChallengeDetailModel>()

        viewModelScope.launch {
            myRepository.getChallengesLiveList(userLang).observe(activity as HomeActivity) {
                dailyChallengesList.clear()
                for (item in it) {
                    challengeDetailList.clear()
                    // get challenge detail (i.e. tasks)
                    for (detail in myRepository.getChallengeDetailListByChallengeName(item.challenge_name,userLang)) {
                        challengeDetailList.add(
                            ChallengeDetailModel(
                                id = detail.id,
                                challenge_name = detail.challenge_name,
                                challenge = detail.challenge,
                                isOpened = detail.isOpened
                            )
                        )
                    }
                    // fetch list from db add to local list
                    dailyChallengesList.add(
                        DailyChallengesModel(
                            id = item.id,
                            challenge_name = item.challenge_name,
                            challenge_description = item.challenge_description,
                            challenges = challengeDetailList,
                            challenge_emoji = item.challenge_emoji,
                            isUserLocal = item.isUserLocal,
                            isOpened = item.isOpened
                        )
                    )
                }
                challengesList.postValue(dailyChallengesList)
                println("checking the list: ${dailyChallengesList.size}")
            }
        }
    }

    fun getCommentData(userLang : String){
        getBlogData()
        getRelaxingMusic()
        db.collection("commons")
            .document(userLang)
            .addSnapshotListener { snapshot, e ->
                if (snapshot == null) {
                    return@addSnapshotListener
                }
                try {
                    daily_quote.value =
                        if ((snapshot?.get("daily_quote") as String).isNullOrEmpty()) ""
                        else snapshot?.get("daily_quote") as String
                }catch (e:Exception){
                    daily_quote.value = "No Quote available"
                }

            }
    }

    fun getBlogData(){
        db.collection("commons")
            .document("blog")
            .addSnapshotListener { snapshot, e ->
                if (snapshot == null) {
                    return@addSnapshotListener
                }
                blog_model.postValue(BlogModel(
                    image_link = snapshot.get("image_link") as String,
                    title = snapshot.get("title") as String,
                    link = snapshot.get("link") as String,
                    isShow = snapshot.get("isShow") as String,
                ))
            }
    }

    fun getRelaxingMusic(){
        db.collection("commons")
            .document("relaxing_music")
            .addSnapshotListener { snapshot, e ->
                if (snapshot == null) {
                    return@addSnapshotListener
                }
                relaxing_music_url.postValue(snapshot.get("url") as String)
            }
    }


    //--------------------------------------------------------------------------------------------------
    fun showLoader(activity: Activity) {
        hudLoader = KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(true)
            .setBackgroundColor(R.color.light_black)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .show();
    }

    fun dismissLoader() {
        if(hudLoader != null){
            hudLoader?.dismiss()
        }
    }

}