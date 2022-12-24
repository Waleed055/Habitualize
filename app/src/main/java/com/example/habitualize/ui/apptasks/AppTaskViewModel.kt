package com.example.habitualize.ui.apptasks

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitualize.R
import com.example.habitualize.db.entities.DBAppTasksModel
import com.example.habitualize.db.repository.MyRepository
import com.example.habitualize.ui.models.AppTaskModel
import com.example.habitualize.utils.*
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppTaskViewModel @Inject constructor(private val myRepository: MyRepository) : ViewModel() {

    var userAppTaskList = MutableLiveData<ArrayList<AppTaskModel>>()
    private var hudLoader: KProgressHUD? = null

    fun insertAppTasks(activity: Activity, langCode: String) {
        myRepository.deleteAppTask()
        var taskList = when(langCode){
            "en"-> appTaskList_en
            "ar"-> appTaskList_ar
            "de"-> appTaskList_de
            "fil"-> appTaskList_fil
            "fr"-> appTaskList_fr
            "hi"-> appTaskList_hi
            "id"-> appTaskList_id
            "it"-> appTaskList_it
            "ja"-> appTaskList_ja
            "pt"-> appTaskList_pt
            "ru"-> appTaskList_ru
            "tr"-> appTaskList_tr
            else-> arrayListOf()
        }


        showLoader(activity)
        viewModelScope.launch{
            for (item in taskList) {
                var model = DBAppTasksModel(
                    reward = item.reward,
                    target = item.target,
                    reward_text = item.reward_text,
                    type = item.type,
                    isCompleted = false,
                )
                myRepository.insertAppTasks(model)
            }
            SharePrefHelper.writeBoolean(isAppTasksLocal, true)
                dismissLoader()
                getAppTasksList(activity)
        }
    }

    fun getAppTasksList(activity: Activity) {
        showLoader(activity)
        var mAppTaskList = arrayListOf<AppTaskModel>()
            myRepository.getAppTasksList().observe(activity as AppTasksActivity) {
                mAppTaskList.clear()
                for (item in it) {
                    mAppTaskList.add(
                        AppTaskModel(
                            id = item.id,
                            reward = item.reward,
                            target = item.target,
                            reward_text = item.reward_text,
                            type = item.type,
                            isCompleted = item.isCompleted,
                        )
                    )
                }
                userAppTaskList.postValue(mAppTaskList)
                dismissLoader()
        }
    }

    fun completeTask(activity: Activity, id: Long){
        showLoader(activity)
        var job = viewModelScope.launch(Dispatchers.IO) {
            myRepository.completeTask(id)
        }
        job.invokeOnCompletion { dismissLoader() }
    }

    //--------------------------------------------------------------------------------------------------
    fun showLoader(activity: Activity) {
        hudLoader = KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
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