package com.example.habitualize.ui.afterauth.feed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habitualize.databinding.ActivityChallengeBinding
import com.example.habitualize.databinding.ActivityFeedBinding
import com.example.habitualize.ui.afterauth.challenge.adapter.DaysAdapter
import com.example.habitualize.ui.afterauth.feed.adapter.FeedAdapter

class FeedActivity : AppCompatActivity() {

    lateinit var binding : ActivityFeedBinding
    lateinit var feedAdapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

    }

    private fun initAdapter() {
        feedAdapter = FeedAdapter(this)
        binding.feedRv.adapter = feedAdapter
    }
}