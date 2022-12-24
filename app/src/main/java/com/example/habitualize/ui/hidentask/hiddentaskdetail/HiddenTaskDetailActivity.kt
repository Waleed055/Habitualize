package com.example.habitualize.ui.hidentask.hiddentaskdetail

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.habitualize.R
import com.example.habitualize.broadcast.AlarmReceiver
import com.example.habitualize.databinding.ActivityHiddenTaskDetailBinding
import com.example.habitualize.db.entities.DBDailyChallengeModel
import com.example.habitualize.ui.home.taskdetail.TaskDetailViewModel
import com.example.habitualize.ui.home.taskdetail.adapter.TaskDetailAdapter
import com.example.habitualize.ui.home.taskdetail.singleday.SingleDayActivity
import com.example.habitualize.ui.models.ChallengeDetailModel
import com.example.habitualize.ui.notification.NotificationModel
import com.example.habitualize.utils.*
import com.jaredrummler.materialspinner.MaterialSpinner
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HiddenTaskDetailActivity : AppCompatActivity(), TaskDetailAdapter.CallBacks {
    private val viewModel: HiddenTaskDetailViewModel by viewModels()
    lateinit var taskDetailAdapter: TaskDetailAdapter
    private var challenge_name = ""
    private var task = ""
    private var tasksList = arrayListOf<ChallengeDetailModel>()

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
    private var isHidden = false


    private lateinit var binding: ActivityHiddenTaskDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiddenTaskDetailBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        userName = SharePrefHelper.readString(userId).toString()
        challenge_name = intent.getStringExtra("challenge_name").orEmpty()


        initObservers()
        initAdapter()
        initListeners()
        menuListeners()
        init()
    }

    private fun init() {
        viewModel.getNotifications(this, userName)
        binding.tvTitle.text = challenge_name
        viewModel.getTasks(
            this,
            challenge_name,
            SharePrefHelper.readString(languageCode).toString()
        )
        viewModel.getChallengeByName(
            challenge_name,
            SharePrefHelper.readString(languageCode).toString()
        )
    }

    private fun initObservers() {
        viewModel.tasksList.observe(this) {
            tasksList.clear()
            taskDetailAdapter.tasksList.clear()
            tasksList.addAll(it)
            taskDetailAdapter.tasksList = tasksList
            taskDetailAdapter.notifyDataSetChanged()
            task = tasksList[(0..29).random()].challenge
        }

        viewModel.challengeData.observe(this) {
            setData(it)
        }

        viewModel.notificationList.observe(this) { list ->
            notificationList = list
        }

    }

    private fun setData(model: DBDailyChallengeModel) {
        binding.challengeDescription.text = model.challenge_description
        if (model.isUserLocal) {
            binding.tvChallengeEmoji.visibility = View.VISIBLE
            binding.ivChallengeEmoji.visibility = View.GONE
            binding.tvChallengeEmoji.text = model.challenge_emoji
        } else {
            binding.tvChallengeEmoji.visibility = View.GONE
            binding.ivChallengeEmoji.visibility = View.VISIBLE
            Glide.with(this)
                .load(Uri.parse(model.challenge_emoji))
                .into(binding.ivChallengeEmoji)
        }
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        taskDetailAdapter = TaskDetailAdapter(
            this,
            tasksList,
            this,
            SharePrefHelper.readInteger(selectedColorIndex)
        )
        binding.taskRecyclerview.adapter = taskDetailAdapter
    }

    private fun menuListeners() {
        binding.circleMenuUnHide.setOnItemClickListener { menuButton ->
            when (menuButton) {
                0 -> {
                    viewModel.unHideChallenge(
                        challenge_name,
                        SharePrefHelper.readString(languageCode).toString()
                    )
                    Handler(Looper.myLooper()!!).postDelayed({finish()},1000)
                }
                1 -> {
                    Handler(Looper.myLooper()!!).postDelayed({ showTimePicker() },1000)
                }
                2 -> {
                    viewModel.restartChallenge(
                        challenge_name,
                        SharePrefHelper.readString(languageCode).toString()
                    )
                }
            }
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
                        notificationList.add(
                            NotificationModel(
                                time = time,
                                title = "$challenge_name",
                                body = "$task"
                            )
                        )
                        setNotification(notificationList)
                        dialog.dismiss()
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
            rightNow[Calendar.HOUR_OF_DAY] = time[0].toInt()
            rightNow[Calendar.MINUTE] = time[1].toInt()
            rightNow[Calendar.AM_PM] = if (time[2] == "AM") 0 else 1
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

    override fun onClickTask(model: ChallengeDetailModel, position: Int) {
        var diff: Long =
            Calendar.getInstance().timeInMillis - if (position > 0) tasksList[position - 1].openDate else 0
        var seconds: Long = diff / 1000
        var minutes: Long = seconds / 60
        var hours: Long = minutes / 60
        var days: Long = hours / 24

        if (model.isOpened || position == 0 || (position > 0 && tasksList[position - 1].isOpened && days.toInt() != 0)) {// check the sequence of the tasks
            var intent = Intent(this, SingleDayActivity::class.java)
            intent.putExtra("task", model)
            intent.putExtra("position", position)
            startActivity(intent)
        } else {
            Toast.makeText(this, resources.getString(R.string.task_available_error), Toast.LENGTH_SHORT).show()
        }
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
        binding.root.setBackgroundColor(ContextCompat.getColor(this, color))
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