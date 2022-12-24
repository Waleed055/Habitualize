package com.example.habitualize.ui.theme

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitualize.db.entities.DBThemeModel
import com.example.habitualize.db.repository.MyRepository
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(val repository: MyRepository): ViewModel() {
    private var hudLoader: KProgressHUD? = null
    var purchasedThemeList = MutableLiveData<ArrayList<Int>>()

    fun purchaseTheme(activity: Activity, model: DBThemeModel){
        showLoader(activity)
        viewModelScope.launch(Dispatchers.Main) {
            repository.purchaseTheme(model)
            dismissLoader()
        }
    }

    fun getPurchasedThemes(activity: Activity){
        repository.getPurchasedThemes().observe(activity as SelectThemeActivity){
            var mPurchasedThemeList = arrayListOf<Int>()
            for (item in it){
                mPurchasedThemeList.add(item.purchased_theme_Index)
            }
            purchasedThemeList.value = mPurchasedThemeList
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