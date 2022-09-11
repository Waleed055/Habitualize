package com.example.habitualize.ui.afterauth.communitychallenges

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habitualize.databinding.ActivityCommunityChallengesBinding
import com.example.habitualize.ui.afterauth.challenge.ChallengeActivity
import com.example.habitualize.ui.afterauth.communitychallenges.adapter.CommunityChallengeAdapter
import com.example.habitualize.ui.afterauth.communitychallenges.communitychallengedetail.CommunityChallengeDetailActivity

class CommunityChallengesActivity : AppCompatActivity(), CommunityChallengeAdapter.CallBacks {

    lateinit var binding: ActivityCommunityChallengesBinding
    private lateinit var adapter: CommunityChallengeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityChallengesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        initAdapter()
    }

    private fun initListeners(){
        binding.createChallengeIcon.setOnClickListener {
            startActivity(Intent(this,ChallengeActivity::class.java))
        }
    }

    private fun initAdapter(){
        adapter = CommunityChallengeAdapter(this,this)
        binding.communityChallengeRecyclerview.adapter = adapter
    }

    override fun onDetailEventCall() {
        startActivity(Intent(this,CommunityChallengeDetailActivity::class.java))
    }

}