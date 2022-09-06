package com.example.habitualize.ui.afterauth.taskschedule

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityNotificationBinding
import com.example.habitualize.databinding.ActivityTaskSheduleBinding
import com.example.habitualize.ui.afterauth.notification.NotificationActivity
import java.util.*

class TaskShedule : AppCompatActivity() {

    lateinit var binding : ActivityTaskSheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskSheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuListeners()

    }

    private fun menuListeners() {
        binding.circleMenu.setOnItemClickListener { menuButton ->
            when(menuButton)
            {
                0-> {}
                1->{startActivity(Intent(this,NotificationActivity::class.java))}
                2->{}
            }
        }
    }
}