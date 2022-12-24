package com.example.habitualize.ui.profile

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitualize.db.repository.MyRepository
import com.example.habitualize.ui.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class ProfileViewModel: ViewModel() {
    private var hudLoader: KProgressHUD? = null

    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // live data
    var user = MutableLiveData<UserModel>()

    fun getUserData(activity: Activity, user_name: String) {
        showLoader(activity)
        db.collection("users")
            .document(user_name)
            .addSnapshotListener { snapshot, e ->
                dismissLoader()
                if (snapshot == null) {
                    return@addSnapshotListener
                }
                var mUser = UserModel(
                    user_name = snapshot?.data?.get("user_name") as String,
                    first_name = snapshot?.data?.get("first_name") as String,
                    last_name = snapshot?.data?.get("last_name") as String,
                    coins = snapshot?.data?.get("coins") as String,
                    profile_image = snapshot?.data?.get("profile_image") as String,
                    password = snapshot?.data?.get("password") as String
                )
                user.value = mUser
            }
    }

    fun updateUserData(
        activity: Activity,
        user_name: String,
        first_name: String,
        last_name: String,
        password: String
    ) {
        showLoader(activity)
        db.collection("users").document(user_name)
            .update("first_name", first_name, "last_name", last_name, "password", password)
            .addOnSuccessListener {
                Toast.makeText(activity, "Updated Successfully!", Toast.LENGTH_SHORT).show()
                dismissLoader()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Update Failed!", Toast.LENGTH_SHORT).show()
                dismissLoader()
            }
    }

    fun updateProfileImage(activity: Activity, imageUri: Uri, user_name: String) {
        showLoader(activity)
        val firebaseRef = FirebaseStorage.getInstance()
            .reference
            .child("users/$user_name/profile_pic/profile_pic.jpg")
        firebaseRef.putFile(imageUri)
            .addOnSuccessListener {
                firebaseRef.downloadUrl.addOnSuccessListener { imgUri ->
                    dismissLoader()
                    db.collection("users").document(user_name)
                        .update("profile_image", imgUri.toString())
                }
            }.addOnFailureListener {
                dismissLoader()
            }
    }


    //--------------------------------------------------------------------------------------------------
    fun showLoader(activity: Activity) {
        hudLoader = KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
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