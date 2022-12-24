package com.example.habitualize.ui.gratitude

import android.app.Activity
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitualize.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.kaopiz.kprogresshud.KProgressHUD

class GratitudeViewModel: ViewModel() {
    private var hudLoader: KProgressHUD? = null

    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var gratitudeRewardList = MutableLiveData<ArrayList<String>>()

    fun getGratitudeList(activity: Activity){
        showLoader(activity)
        db.collection("gratitude")
            .document("gratitude_rewards")
            .addSnapshotListener { snapshot, error ->
                if (snapshot == null) {
                    return@addSnapshotListener
                }

                if (snapshot.data?.get("rewards_list") != null) {
                    gratitudeRewardList.value = snapshot.data?.get("rewards_list") as ArrayList<String>
                }

                Handler(Looper.myLooper()!!).postDelayed({ // take time to load data from firestore
                    dismissLoader()
                }, 500)
            }
    }


    //--------------------------------------------------------------------------------------------------
    private fun showLoader(activity: Activity) {
        hudLoader = KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setBackgroundColor(R.color.light_black)
            .setDimAmount(0.5f)
            .show();
    }

    private fun dismissLoader() {
        if (hudLoader != null) {
            hudLoader?.dismiss()
        }
    }
}