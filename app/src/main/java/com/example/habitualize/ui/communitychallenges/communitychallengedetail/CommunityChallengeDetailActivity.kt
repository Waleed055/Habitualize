package com.example.habitualize.ui.communitychallenges.communitychallengedetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.browser.trusted.sharing.ShareTarget
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityCommunityChallengeDetailBinding
import com.example.habitualize.ui.challenge.ChallengeActivity
import com.example.habitualize.ui.communitychallenges.communitychallengedetail.adapter.CommunityChallengeDetailAdapter
import com.example.habitualize.ui.home.HomeActivity
import com.example.habitualize.ui.models.CommunityChallengeModel
import com.example.habitualize.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityChallengeDetailActivity : AppCompatActivity() {
    private val viewModel: CommunityChallengeDetailViewModel by viewModels()

    lateinit var binding: ActivityCommunityChallengeDetailBinding
    private lateinit var adapter: CommunityChallengeDetailAdapter
    private var challenge_id = ""
    private lateinit var challengeData: CommunityChallengeModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityChallengeDetailBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        challenge_id = intent.getStringExtra("challenge_id").orEmpty()
        viewModel.getChallengeData(this,challenge_id)

        initAdapter()
        initObserver()
        initListeners()
    }

    private fun initListeners(){
        binding.ivLikeIcon.setOnClickListener {
            if(!challengeData.likes.contains(SharePrefHelper.readString(userId))){
                viewModel.hitLike(this,challengeData,SharePrefHelper.readString(userId).orEmpty())
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.cvStartChallenge.setOnClickListener {
            //----------------------------------------------------------------------
            //counter
            var _counter  = SharePrefHelper.readInteger(addTaskCounter) + 1
            SharePrefHelper.writeInteger(addTaskCounter, _counter)

            viewModel.addChallengeToDB(
                this,
                challenge_name = challengeData.challenge_name,
                challenge_description = challengeData.challenge_description,
                challenge_emoji = challengeData.challenge_emoji,
                language_code = challengeData.language_code,
                tasksList = challengeData.challenge_list
            )
        }

        binding.llEditIcon.setOnClickListener {
            var intent = Intent(this, ChallengeActivity::class.java)
            intent.putExtra("whichScreen", 2)
            intent.putExtra("challenge_id",challenge_id)
            startActivity(intent)
        }

    }

    private fun initObserver(){
        viewModel.challengeData.observe(this){
            challengeData = it
            setData(it)
        }

        viewModel.isChallengeAdded.observe(this){
            if(it){
                var intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

    }

    private fun setData(data: CommunityChallengeModel){
        if(data.user_id == SharePrefHelper.readString(userId)){
            binding.llEditIcon.visibility = View.VISIBLE
        }else{
            binding.llEditIcon.visibility = View.GONE
        }
        binding.tvChallengeCreatedBy.text = "${resources.getString(R.string.created_by)} ${data.user_name}"
        binding.tvChallengeTitle.text = data.challenge_name
        binding.tvChallengeDescription.text = data.challenge_description
        binding.tvChallengeEmoji.text = data.challenge_emoji
        if(data.likes.contains(SharePrefHelper.readString(userId))){
            binding.ivLikeIcon.setColorFilter(ContextCompat.getColor(this, R.color.theme_light))
        }else{
            binding.ivLikeIcon.setColorFilter(ContextCompat.getColor(this, R.color.white))
        }
        adapter.challengeList = data.challenge_list
        adapter.notifyDataSetChanged()
    }

    private fun initAdapter(){
        adapter = CommunityChallengeDetailAdapter(this, arrayListOf(), SharePrefHelper.readInteger(selectedColorIndex))
        binding.communityChallengeDetailRecyclerview.adapter = adapter
    }

    private fun updateTheme(position: Int){
        when(position){
            0->{ setThemeColor(R.color.theme, R.color.theme) }
            1->{ setThemeColor(R.color.theme_2, R.color.theme_2_light) }
            2->{ setThemeColor(R.color.theme_3, R.color.theme_3) }
            3->{ setThemeColor(R.color.theme_4, R.color.theme_4) }
            4->{ setThemeColor(R.color.theme_5, R.color.theme_5) }
            5->{ setThemeColor(R.color.theme_6, R.color.theme_6) }
            6->{ setThemeColor(R.color.theme_7, R.color.theme_7) }
            7->{ setThemeColor(R.color.theme_8, R.color.theme_8) }
        }
    }

    private fun setThemeColor(color: Int, lightColor: Int){
        changeStatusBarColor(this, color)
        binding.root.setBackgroundColor(ContextCompat.getColor(this, color))
        binding.cvStartChallenge.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.ivLikeIcon.setColorFilter(ContextCompat.getColor(this, lightColor))
    }

}