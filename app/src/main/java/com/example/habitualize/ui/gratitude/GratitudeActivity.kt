package com.example.habitualize.ui.gratitude

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cooltechworks.views.ScratchImageView
import com.cooltechworks.views.ScratchTextView
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityGratitudeBinding
import com.example.habitualize.managers.AdsManager
import com.example.habitualize.utils.*
import com.google.android.gms.tasks.OnSuccessListener
import java.util.*
import kotlin.random.Random


class GratitudeActivity : AppCompatActivity() {
    private val viewModel: GratitudeViewModel by viewModels()
    lateinit var binding: ActivityGratitudeBinding
    private var reward_1 = 20
    private var reward_2 = 20
    private var coins = 0
    private var isAdLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGratitudeBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        AdsManager.getInstance()?.loadNative(binding.adLayout, layoutInflater, R.layout.native_ad_without_media_layout)
        AdsManager.getInstance().loadRewardedAd(this) { bool -> isAdLoaded = bool }

        binding.includedLayout.tvTitle.text =  resources.getString(R.string.gratitude)
        binding.stvScratchCard1.text = "$reward_1 ${resources.getString(R.string.coins)}"
        binding.stvScratchCard2.text = "$reward_2 ${resources.getString(R.string.coins)}"
        coins = try { SharePrefHelper.readString(userCoins)?.toInt()!! }catch (e: Exception){ 0 }
        initListeners()
        initObserver()
        updateUI()

        viewModel.getGratitudeList(this)
    }

    private fun updateUI(){
        if(SharePrefHelper.readString(gratitudeRevealDate)?.isEmpty() == true){
            SharePrefHelper.writeString(gratitudeRevealDate, "${Calendar.getInstance().timeInMillis}")
            if(SharePrefHelper.readBoolean(gratitudeReward1)){
                binding.stvScratchCard1.visibility = View.GONE
                binding.tvNotAvailable1.visibility = View.VISIBLE
            }else{
                binding.stvScratchCard1.visibility = View.VISIBLE
                binding.tvNotAvailable1.visibility = View.GONE
            }
            if(SharePrefHelper.readBoolean(gratitudeReward2)){
                binding.stvScratchCard2.visibility = View.GONE
                binding.tvNotAvailable2.visibility = View.VISIBLE
            }else{
                binding.stvScratchCard2.visibility = View.VISIBLE
                binding.tvNotAvailable2.visibility = View.GONE
            }
        }else if(calculateTimeDiff(SharePrefHelper.readString(gratitudeRevealDate).toString().toLong()) == 0L){
            SharePrefHelper.writeString(gratitudeRevealDate, "${Calendar.getInstance().timeInMillis}")
            if(SharePrefHelper.readBoolean(gratitudeReward1)){
                binding.stvScratchCard1.visibility = View.GONE
                binding.tvNotAvailable1.visibility = View.VISIBLE
            }else{
                binding.stvScratchCard1.visibility = View.VISIBLE
                binding.tvNotAvailable1.visibility = View.GONE
            }
            if(SharePrefHelper.readBoolean(gratitudeReward2)){
                binding.stvScratchCard2.visibility = View.GONE
                binding.tvNotAvailable2.visibility = View.VISIBLE
            }else{
                binding.stvScratchCard2.visibility = View.VISIBLE
                binding.tvNotAvailable2.visibility = View.GONE
            }
        }else{
            SharePrefHelper.writeString(gratitudeRevealDate, "${Calendar.getInstance().timeInMillis}")
            SharePrefHelper.writeBoolean(gratitudeReward1,false)
            SharePrefHelper.writeBoolean(gratitudeReward2,false)
            binding.stvScratchCard1.visibility = View.VISIBLE
            binding.tvNotAvailable1.visibility = View.GONE
            binding.stvScratchCard2.visibility = View.VISIBLE
            binding.tvNotAvailable2.visibility = View.GONE
        }
        updateWatchBtnVisibility()
    }

    private fun updateWatchBtnVisibility(){
        if (SharePrefHelper.readBoolean(gratitudeReward1) && SharePrefHelper.readBoolean(gratitudeReward2)){
            binding.cvWatchAdBtn.visibility = View.VISIBLE
        }else{
            binding.cvWatchAdBtn.visibility = View.GONE
        }
    }

    private fun initObserver() {
        viewModel.gratitudeRewardList.observe(this) {
            if (it.isNotEmpty() && it.size < 2) {
                reward_1 = it[0].toInt()
                reward_2 = 20
                binding.stvScratchCard1.text = "$reward_1 ${resources.getString(R.string.coins)}"
                binding.stvScratchCard2.text = "$reward_2 ${resources.getString(R.string.coins)}"
            } else if (it.isNotEmpty()) {
                var randomIndex = (0..(it.size - 2)).random()
                reward_1 = it[randomIndex].toInt()
                reward_2 = it[randomIndex + 1].toInt()
                binding.stvScratchCard1.text = "$reward_1 ${resources.getString(R.string.coins)}"
                binding.stvScratchCard2.text = "$reward_2 ${resources.getString(R.string.coins)}"
            }
        }
    }

    private fun initListeners() {

        binding.includedLayout.backButton.setOnClickListener {
            finish()
        }

        binding.cvWatchAdBtn.setOnClickListener {
            if(isAdLoaded){
                AdsManager.getInstance().showRewardedVideo(this){ bool ->
                    if(bool){
                        SharePrefHelper.writeBoolean(gratitudeReward1, false)
                        SharePrefHelper.writeBoolean(gratitudeReward2, false)
                        recreate()
                    }else{
                        Toast.makeText(this,"Please watch full video to get the reward!", Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                Toast.makeText(this,"No Ad Available yet!", Toast.LENGTH_LONG).show()
            }

        }

        binding.stvScratchCard1.setRevealListener(object : ScratchTextView.IRevealListener {
            var isReveal = false
            override fun onRevealed(tv: ScratchTextView?) {}
            override fun onRevealPercentChangedListener(stv: ScratchTextView?, percent: Float) {
                if (percent > 0.60 && !isReveal) {
                    isReveal = true
                    SharePrefHelper.writeBoolean(gratitudeReward1, true)
                    coins += reward_1
                    SharePrefHelper.writeString(userCoins,"$coins")
                    Toast.makeText(
                        this@GratitudeActivity,
                        " ${resources.getString(R.string.you_have_received)} ${stv?.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateWatchBtnVisibility()

                    //----------------------------------------------------------------------
                    //counter
                    var _counter  = SharePrefHelper.readInteger(gratitudeScratchCounter) + 1
                    SharePrefHelper.writeInteger(gratitudeScratchCounter, _counter)
                }
            }
        })
        binding.stvScratchCard2.setRevealListener(object : ScratchTextView.IRevealListener {
            var isReveal = false
            override fun onRevealed(tv: ScratchTextView?) {}
            override fun onRevealPercentChangedListener(stv: ScratchTextView?, percent: Float) {
                if (percent > 0.60 && !isReveal) {
                    isReveal = true
                    SharePrefHelper.writeBoolean(gratitudeReward2, true)
                    coins += reward_2
                    SharePrefHelper.writeString(userCoins,"$coins")
                    Toast.makeText(
                        this@GratitudeActivity,
                        "${resources.getString(R.string.you_have_received)} ${stv?.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateWatchBtnVisibility()
                }
            }
        })
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
        binding.cvWatchAdBtn.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.stvScratchCard1.setTextColor(ContextCompat.getColor(this, color))
        binding.stvScratchCard2.setTextColor(ContextCompat.getColor(this, color))
    }
}