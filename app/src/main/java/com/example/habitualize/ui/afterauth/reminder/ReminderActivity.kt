package com.example.habitualize.ui.afterauth.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityReminderBinding
import com.example.habitualize.ui.afterauth.reminder.adapter.ReminderAdapter

class ReminderActivity : AppCompatActivity() {

    lateinit var binding : ActivityReminderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

    }

    private fun initAdapter() {
        var adapter  = ReminderAdapter(this)
        binding.reminderRv.adapter = adapter
    }
}