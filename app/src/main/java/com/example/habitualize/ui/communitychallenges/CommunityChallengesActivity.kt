package com.example.habitualize.ui.communitychallenges

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityCommunityChallengesBinding
import com.example.habitualize.ui.apptasks.AppTasksActivity
import com.example.habitualize.ui.challenge.ChallengeActivity
import com.example.habitualize.ui.communitychallenges.adapter.CommunityChallengeAdapter
import com.example.habitualize.ui.communitychallenges.communitychallengedetail.CommunityChallengeDetailActivity
import com.example.habitualize.ui.models.CommunityChallengeModel
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.changeStatusBarColor
import com.example.habitualize.utils.languageCode
import com.example.habitualize.utils.selectedColorIndex
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityChallengesActivity : AppCompatActivity(), CommunityChallengeAdapter.CallBacks {
    private val viewModel: CommunityChallengeViewModel by viewModels()

    lateinit var binding: ActivityCommunityChallengesBinding
    private lateinit var adapter: CommunityChallengeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityChallengesBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        binding.tvTitle.text = resources.getString(R.string.community_challenges)
        setContentView(binding.root)


        initListeners()
        initAdapter()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCommunityChallengesByLanguageCode(this, SharePrefHelper.readString(languageCode).toString())
    }

    private fun initObserver(){
        viewModel.challengeList.observe(this){
            if(it.isEmpty()){
                binding.dataLayout.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
                return@observe
            }else{
                binding.dataLayout.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
            }
            adapter.challenge_list = it
            adapter.notifyDataSetChanged()
        }
    }


    private fun initListeners(){
        binding.createChallengeIcon.setOnClickListener {
            var intent = Intent(this, ChallengeActivity::class.java)
            intent.putExtra("whichScreen", 1)
            startActivity(intent)
        }
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun initAdapter(){
        adapter = CommunityChallengeAdapter(this, arrayListOf(),this, SharePrefHelper.readInteger(selectedColorIndex))
        binding.communityChallengeRecyclerview.adapter = adapter
    }

    override fun onDetailEventCall(challenge_id: String) {
        var intent = Intent(this,CommunityChallengeDetailActivity::class.java)
        intent.putExtra("challenge_id",challenge_id)
        startActivity(intent)
    }

    private fun updateTheme(position: Int){
        when(position){
            0->{ setThemeColor(R.color.theme) }
            1->{ setThemeColor(R.color.theme_2) }
            2->{ setThemeColor(R.color.theme_3) }
            3->{ setThemeColor(R.color.theme_4) }
            4->{ setThemeColor(R.color.theme_5) }
            5->{ setThemeColor(R.color.theme_6) }
            6->{ setThemeColor(R.color.theme_7) }
            7->{ setThemeColor(R.color.theme_8) }
        }
    }

    private fun setThemeColor(color: Int){
        changeStatusBarColor(this, color)
        binding.root.setBackgroundColor(ContextCompat.getColor(this, color))
    }
}