package com.example.habitualize.ui.reminder

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityReminderBinding
import com.example.habitualize.ui.home.taskdetail.TaskDetailActivity
import com.example.habitualize.ui.models.AllRemindersModel
import com.example.habitualize.ui.models.DailyChallengesModel
import com.example.habitualize.ui.models.ReminderModel
import com.example.habitualize.ui.reminder.adapter.ReminderAdapter
import com.example.habitualize.utils.*
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text

@AndroidEntryPoint
class ReminderActivity : AppCompatActivity() {
    private val viewModel: ReminderViewModel by viewModels()
    lateinit var binding: ActivityReminderBinding
    lateinit var adapter: ReminderAdapter
    lateinit var allRemindersModel: AllRemindersModel
    private var coins = 0
    private var button: CardView? = null
    private var tv_quote_title: TextView? = null
    private var tv_reminder_quote: TextView? = null
    private var iv_dismiss_btn: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        coins = try {
            SharePrefHelper.readString(userCoins)?.toInt()!!
        } catch (e: Exception) {
            0
        }
        binding.tvCoinCounter.text = "$coins"
        initAdapter()
        initObservers()
        initListeners()

        viewModel.getUserReminders(
            this,
            SharePrefHelper.readString(languageCode).toString(),
            SharePrefHelper.readString(userId).toString()
        )
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        adapter = ReminderAdapter(
            this,
            SharePrefHelper.readInteger(selectedColorIndex),
            object : ReminderAdapter.CallBacks {
                override fun onOpenEventCall(
                    reminder: String,
                    langCode: String,
                    isOpened: Boolean
                ) {
                    if (isOpened) {
                        showInfoDialog(reminder)
                    } else {
                        showConfirmationDialog(reminder, langCode)
                    }
                }
            },
            SharePrefHelper.readString(languageCode).toString()
        )
        binding.reminderRv.adapter = adapter
    }

    private fun initObservers() {
        viewModel.allRemindersModel.observe(this) {
            if (it.all_reminders.isEmpty()) {
                binding.dataLayout.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
                return@observe
            } else {
                binding.dataLayout.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
            }
            allRemindersModel = AllRemindersModel()
            allRemindersModel = it
            adapter.allReminders = allRemindersModel
            adapter.notifyDataSetChanged()
        }
    }

    private fun showConfirmationDialog(reminder: String, langCode: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.item_open_reminder_dialouge)
        button = dialog.findViewById<CardView>(R.id.cv_open_reminder_btn)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        dialog.show()

        button?.setOnClickListener {
            if(coins >= 50){
                //----------------------------------------------------
                coins -= 50
                SharePrefHelper.writeString(userCoins, "$coins")
                binding.tvCoinCounter.text = "$coins"
                //-----------------------------------------------------
                var reminderModel = ReminderModel(reminder = reminder, language_code = langCode)
                viewModel.addOpenedReminder(
                    this@ReminderActivity,
                    SharePrefHelper.readString(userId).toString(),
                    reminderModel
                )
                dialog.dismiss()
            }else {
                Toast.makeText(this, noCoinMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showInfoDialog(reminder: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.item_info_reminder_dialouge)
        iv_dismiss_btn = dialog.findViewById<ImageView>(R.id.iv_dismiss_btn)
        tv_reminder_quote = dialog.findViewById<TextView>(R.id.tv_reminder_quote)
        tv_quote_title = dialog.findViewById<TextView>(R.id.tv_quote_title)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        dialog.show()

        tv_reminder_quote?.text = reminder
        iv_dismiss_btn?.setOnClickListener {
            dialog.dismiss()
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
        binding.premiumButton.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.root.setBackgroundColor(ContextCompat.getColor(this, color))
        button.let { it?.setCardBackgroundColor(ContextCompat.getColor(this, color)) }
        tv_quote_title.let { it?.setTextColor(ContextCompat.getColor(this, color)) }
        iv_dismiss_btn.let { it?.setColorFilter(ContextCompat.getColor(this, color)) }
    }
}