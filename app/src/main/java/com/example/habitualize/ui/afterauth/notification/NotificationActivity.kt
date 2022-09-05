package com.example.habitualize.ui.afterauth.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    lateinit var binding : ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }



}