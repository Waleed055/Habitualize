package com.example.habitualize.ui.home.viewmodels

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitualize.R
import com.example.habitualize.db.repository.MyRepository
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.selectedColorIndex
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val myRepository: MyRepository): ViewModel()  {
    private var hudLoader: KProgressHUD? = null
    var isLogOut = MutableLiveData<Boolean>()

    fun deleteAllData(activity: Activity){
        showLoader(activity)
        viewModelScope.launch(Dispatchers.IO) {
            SharePrefHelper.clearAll(
                SharePrefHelper.readInteger(selectedColorIndex),
                Locale.getDefault().language
            )
            myRepository.deleteAllData()
        }.invokeOnCompletion{
            viewModelScope.launch(Dispatchers.Main) {
                isLogOut.postValue(true)
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