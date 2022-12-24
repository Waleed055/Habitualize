package com.example.habitualize.ui.hidentask

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitualize.R
import com.example.habitualize.db.repository.MyRepository
import com.example.habitualize.ui.home.HomeActivity
import com.example.habitualize.ui.models.ChallengeDetailModel
import com.example.habitualize.ui.models.DailyChallengesModel
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiddenTaskViewModel @Inject constructor(private val myRepository: MyRepository) : ViewModel()  {

    var challengesList = MutableLiveData<ArrayList<DailyChallengesModel>>()
    var mChallengesList = ArrayList<DailyChallengesModel>()
    private var hudLoader: KProgressHUD? = null

    fun getHiddenChallenges(activity: Activity, userLang : String) {
        var dailyChallengesList = arrayListOf<DailyChallengesModel>()
        var challengeDetailList = arrayListOf<ChallengeDetailModel>()

        viewModelScope.launch {
            myRepository.getHiddenChallengesLiveList(userLang).observe(activity as HiddenTaskActivity) {
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
            }
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