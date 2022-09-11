package com.example.habitualize.ui.afterauth.communitychallenges.communitychallengedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityCommunityChallengeDetailBinding
import com.example.habitualize.ui.afterauth.communitychallenges.communitychallengedetail.adapter.CommunityChallengeDetailAdapter

class CommunityChallengeDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityCommunityChallengeDetailBinding
    private lateinit var adapter: CommunityChallengeDetailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityChallengeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
    }

    private fun initAdapter(){
        adapter = CommunityChallengeDetailAdapter()
        binding.communityChallengeDetailRecyclerview.adapter = adapter
    }

}