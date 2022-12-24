package com.example.habitualize.ui.reminder

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitualize.db.entities.DBCompleteReminderModel
import com.example.habitualize.db.repository.MyRepository
import com.example.habitualize.ui.models.AllRemindersModel
import com.example.habitualize.ui.models.FeedCommentsModel
import com.example.habitualize.ui.models.ReminderModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(val repository: MyRepository) : ViewModel() {
    private var hudLoader: KProgressHUD? = null

    //db
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isReminderAdded = MutableLiveData<Boolean>()
    var isReminderDeleted = MutableLiveData<Boolean>()

    var allRemindersModel = MutableLiveData<AllRemindersModel>()

    var opened_reminders = ArrayList<ReminderModel>()
    var reminderList = ArrayList<String>()


    fun addOpenedReminder(activity: Activity, user_id: String, reminderModel: ReminderModel) {
        showLoader(activity)
        viewModelScope.launch(Dispatchers.Main) {
            repository.insertCompletedReminder(
                DBCompleteReminderModel(
                    reminderModel.reminder,
                    reminderModel.language_code
                )
            )
            dismissLoader()
        }
    }

    fun getUserReminders(activity: Activity, languageCode: String, user_id: String) {
        showLoader(activity)
        viewModelScope.launch(Dispatchers.Main) {
            repository.getCompletedReminderList().observe(activity as ReminderActivity){ list->
                list.forEach { reminder ->
                    opened_reminders.add(
                        ReminderModel(
                            reminder = reminder.reminder,
                            language_code = reminder.language_code
                        )
                    )
                }
                getAllRemindersByLanguageCode(activity, languageCode, opened_reminders)
            }
        }
    }

    fun getAllRemindersByLanguageCode(
        activity: Activity,
        languageCode: String,
        opened_reminders: ArrayList<ReminderModel>
    ) {
        db.collection("reminder")
            .document(languageCode)
            .get()
            .addOnSuccessListener { document ->
                if (document.data?.get("reminder_list") != null) {
                    reminderList = document.data?.get("reminder_list") as ArrayList<String>
                }
                Handler(Looper.myLooper()!!).postDelayed({ // take time to load data from firestore
                    allRemindersModel.value = AllRemindersModel(
                        opened_reminders = opened_reminders,
                        all_reminders = reminderList
                    )
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
            .setDimAmount(0.5f)
            .show();
    }

    private fun dismissLoader() {
        if (hudLoader != null) {
            hudLoader?.dismiss()
        }
    }
}