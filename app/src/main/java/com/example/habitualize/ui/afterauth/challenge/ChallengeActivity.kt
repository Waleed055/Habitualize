package com.example.habitualize.ui.afterauth.challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityChallengeBinding
import com.example.habitualize.databinding.ActivityNotificationBinding
import com.example.habitualize.ui.afterauth.challenge.adapter.DaysAdapter

class ChallengeActivity : AppCompatActivity() {

    lateinit var binding : ActivityChallengeBinding
    lateinit var daysAdapter : DaysAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

    }

    private fun initAdapter() {
        daysAdapter = DaysAdapter(this)
        binding.daysRv.adapter = daysAdapter
    }
}