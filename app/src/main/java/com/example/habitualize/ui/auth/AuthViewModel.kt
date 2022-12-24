package com.example.habitualize.ui.auth

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitualize.R
import com.example.habitualize.db.repository.MyRepository
import com.example.habitualize.ui.auth.login.LoginActivity
import com.example.habitualize.ui.home.viewmodels.HomeViewModel
import com.example.habitualize.ui.models.ReminderModel
import com.example.habitualize.ui.notification.NotificationModel
import com.example.habitualize.utils.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(val repository: MyRepository) : ViewModel() {
    //tags
    private val TAG = "AuthViewModel"
    private var hudLoader: KProgressHUD? = null

    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    //live data
    var isSignUpSuccessful = MutableLiveData<Boolean>()
    var isLoginSuccessful = MutableLiveData<Boolean>()


    //other data
    var userNameList = arrayListOf<String>()
    lateinit var users: DocumentReference

    fun doSignUp(
        activity: Activity,
        user_name: String,
        first_name: String,
        last_name: String,
        password: String
    ) {
        showLoader(activity)
        val user = hashMapOf(
            "user_name" to user_name,
            "first_name" to first_name,
            "last_name" to last_name,
            "password" to password,
            "coins" to "50",
            "profile_image" to "android.resource://com.waleed.habitualize.public/drawable/profile_icon",
            "notifications_time" to arrayListOf<NotificationModel>(),
            "opened_reminders" to arrayListOf<ReminderModel>()
        )

        // Add a new document with a user UID
        db.collection("users").document(user_name)
            .set(user)
            .addOnSuccessListener {
                SharePrefHelper.writeString(userId, user_name)
                SharePrefHelper.writeString(userName, "$first_name $last_name")
                SharePrefHelper.writeString(
                    userImage,
                    "android.resource://com.waleed.habitualize.public/drawable/profile_icon"
                )
                SharePrefHelper.writeString(userCoins, "50")
                SharePrefHelper.writeBoolean(isLogIn, true)
                isSignUpSuccessful.value = true
                dismissLoader()
            }
            .addOnFailureListener {
                dismissLoader()
                isSignUpSuccessful.value = false
            }
    }

    fun doLogin(
        activity: Activity,
        user_name: String,
        password: String
    ) {
        showLoader(activity)
        var isUserExist = false
        db.collection("users").get().addOnSuccessListener {
            for (document in it.documents) {
                if (user_name == document.getString("user_name") &&
                    password == document.getString("password")
                ) {
                    SharePrefHelper.writeString(userId, document.getString("user_name"))
                    SharePrefHelper.writeString(
                        userName,
                        "${document.getString("first_name")} ${document.getString("last_name")}"
                    )
                    SharePrefHelper.writeString(userImage, document.getString("profile_image"))
                    SharePrefHelper.writeString(userCoins, "50")
                    SharePrefHelper.writeBoolean(isLogIn, true)
                    isUserExist = true
                    dismissLoader()
                    break
                }
            }
            isLoginSuccessful.value = isUserExist
        }
            .addOnFailureListener {
                dismissLoader()
                isLoginSuccessful.value = isUserExist
            }
    }

    fun getAllUserName(activity: Activity) {
        showLoader(activity)
        userNameList.clear()
        db.collection("users").get()
            .addOnSuccessListener {
                for (document in it.documents) {
                    userNameList.add(document.getString("user_name").orEmpty())
                }
                dismissLoader()
            }
            .addOnFailureListener {
                dismissLoader()
            }
    }

    fun getData(activity: LoginActivity) {
        repository.getPurchasedThemes().observe(activity) {
            println("checking the list size: $it")
        }
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