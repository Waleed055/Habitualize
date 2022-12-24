package com.example.habitualize.ui.notification

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.*
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.broadcast.AlarmReceiver
import com.example.habitualize.databinding.ActivityNotificationBinding
import com.example.habitualize.managers.AdsManager
import com.example.habitualize.ui.notification.adapter.NotificationsAdapter
import com.example.habitualize.ui.profile.ProfileViewModel
import com.example.habitualize.utils.*
import com.jaredrummler.materialspinner.MaterialSpinner
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationActivity : AppCompatActivity() {
    private val viewModel: NotificationViewModel by viewModels()
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var adapter: NotificationsAdapter
    private var header: RelativeLayout? = null
    private var tv_colon: TextView? = null
    private var spinner_hours: MaterialSpinner? = null
    private var spinner_mins: MaterialSpinner? = null
    private var tv_select_time_title: TextView? = null
    private var spinner_am_pm: MaterialSpinner? = null
    private var cv_cancel_icon: CardView? = null
    private var cv_add_icon: CardView? = null
    private var userName = ""
    private var notificationList = arrayListOf<NotificationModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        binding.notificationIncludedLayout.tvTitle.text = resources.getString(R.string.notifications)
        setContentView(binding.root)
        binding.smNotificationSwitch.isChecked = SharePrefHelper.readBoolean(isNotificationAllow)
        userName = SharePrefHelper.readString(userId).toString()
        viewModel.getNotifications(this, userName)


        initListeners()
        initAdapter()
        initObservers()
    }

    private fun initListeners() {
        binding.fabCard.setOnClickListener {
            showTimePicker()
        }

        binding.notificationIncludedLayout.backButton.setOnClickListener {
            finish()
        }

        binding.smNotificationSwitch.setOnClickListener {
            SharePrefHelper.writeBoolean(isNotificationAllow,binding.smNotificationSwitch.isChecked)
        }

    }

    private fun initAdapter() {
        adapter = NotificationsAdapter(this, object : NotificationsAdapter.CallBacks {
            override fun onNotificationDelete(position: Int) {
                notificationList.removeAt(position)
                setNotification(notificationList)
            }
        }, SharePrefHelper.readInteger(selectedColorIndex), arrayListOf())
        binding.rlNotifications.adapter = adapter
    }

    private fun initObservers() {
        viewModel.notificationList.observe(this) { list ->
            notificationList = list
            adapter.notificationList = notificationList
            adapter.notifyDataSetChanged()
        }
    }

    private fun showTimePicker() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.item_time_picker_dialog)

        header = dialog.findViewById(R.id.header)
        spinner_hours = dialog.findViewById(R.id.spinner_hours)
        spinner_mins = dialog.findViewById(R.id.spinner_mins)
        tv_colon = dialog.findViewById(R.id.tv_colon)
        spinner_am_pm = dialog.findViewById(R.id.spinner_am_pm)
        cv_cancel_icon = dialog.findViewById(R.id.cv_cancel_icon)
        cv_add_icon = dialog.findViewById(R.id.cv_add_icon)
        tv_select_time_title = dialog.findViewById(R.id.tv_select_time_title)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        dialog.show()
        var am_pm = ""
        var hours = ""
        var mins = ""
        spinner_am_pm?.setItems("AM", "PM")
        spinner_am_pm?.setOnItemSelectedListener { view, position, id, item ->
            am_pm = item as String
        }
        spinner_hours?.setItems(
            "01",
            "02",
            "03",
            "04",
            "05",
            "06",
            "07",
            "08",
            "09",
            "10",
            "11",
            "12"
        )
        spinner_hours?.setOnItemSelectedListener { view, position, id, item ->
            hours = item as String
        }
        var minsList = arrayListOf<String>()
        for (index in 0..59) {
            if (index.toString().length < 2) {
                minsList.add("0$index")
            } else {
                minsList.add("$index")
            }
        }

        spinner_mins?.setItems(minsList)
        spinner_mins?.setOnItemSelectedListener { view, position, id, item ->
            mins = item as String
        }

        cv_add_icon?.setOnClickListener {
            if (hours.isNotEmpty()) {
                if (mins.isNotEmpty()) {
                    if (am_pm.isNotEmpty()) {
                        var time = "$hours:$mins:$am_pm"
                        notificationList.add(NotificationModel(time = time, title = resources.getString(R.string.notification_title), body = resources.getString(R.string.notification_body)))
                        setNotification(notificationList)
                        binding.smNotificationSwitch.isChecked = true
                        SharePrefHelper.writeBoolean(isNotificationAllow, true)
                        dialog.dismiss()
                        AdsManager.getInstance().showInterstitial(this)
                    } else {
                        Toast.makeText(this, resources.getString(R.string.am_pm_error), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, resources.getString(R.string.minutes_error), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, resources.getString(R.string.hours_error), Toast.LENGTH_SHORT).show()
            }
        }

        cv_cancel_icon?.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setNotification(list: ArrayList<NotificationModel>) {
        list.forEachIndexed { index, notification ->
            var time = notification.time.split(":")
            val rightNow = Calendar.getInstance()
            rightNow[Calendar.HOUR_OF_DAY] = if (time[2] == "AM") time[0].toInt() else 12 + time[0].toInt()
            rightNow[Calendar.MINUTE] = time[1].toInt()
            rightNow.set(Calendar.SECOND, 0)
            startAlarm(rightNow, index, notification.title, notification.body)
        }
        viewModel.updateNotifications(this, userName, list)
    }

    private fun startAlarm(calendar: Calendar, index: Int, title: String, body: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("title", title)
        intent.putExtra("body", body)
        val flags = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            else -> PendingIntent.FLAG_UPDATE_CURRENT
        }

        val pendingIntent = PendingIntent.getBroadcast(this, index, intent, flags)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun updateTheme(position: Int) {
        when (position) {
            0 -> {
                setThemeColor(R.color.theme)
            }
            1 -> {
                setThemeColor(R.color.theme_2)
            }
            2 -> {
                setThemeColor(R.color.theme_3)
            }
            3 -> {
                setThemeColor(R.color.theme_4)
            }
            4 -> {
                setThemeColor(R.color.theme_5)
            }
            5 -> {
                setThemeColor(R.color.theme_6)
            }
            6 -> {
                setThemeColor(R.color.theme_7)
            }
            7 -> {
                setThemeColor(R.color.theme_8)
            }
        }
    }

    private fun setThemeColor(color: Int) {
        changeStatusBarColor(this, color)
        binding.roor.setBackgroundColor(ContextCompat.getColor(this, color))
        binding.fabCard.setCardBackgroundColor(ContextCompat.getColor(this, color))
        header.let { it?.setBackgroundColor(ContextCompat.getColor(this, color)) }
        tv_colon.let { it?.setTextColor(ContextCompat.getColor(this, color)) }
        tv_select_time_title.let { it?.setTextColor(ContextCompat.getColor(this, color)) }
        spinner_am_pm.let { it?.setHintColor(ContextCompat.getColor(this, color)) }
        spinner_hours.let { it?.setHintColor(ContextCompat.getColor(this, color)) }
        spinner_mins.let { it?.setHintColor(ContextCompat.getColor(this, color)) }
        cv_cancel_icon.let { it?.setCardBackgroundColor(ContextCompat.getColor(this, color)) }
        cv_add_icon.let { it?.setCardBackgroundColor(ContextCompat.getColor(this, color)) }
    }
}