package com.example.habitualize.ui.theme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivitySelectThemeBinding
import com.example.habitualize.db.entities.DBThemeModel
import com.example.habitualize.ui.splash.SplashActivity
import com.example.habitualize.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectThemeActivity : AppCompatActivity() {
    private val viewModel: ThemeViewModel by viewModels()
    private lateinit var binding: ActivitySelectThemeBinding
    private lateinit var adapter: ThemeAdapter
    private var selectedTheme = 0
    var purchasedThemeList = arrayListOf<Int>()
    private var coins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectThemeBinding.inflate(layoutInflater)
        selectedTheme = SharePrefHelper.readInteger(selectedColorIndex)
        updateTheme(selectedTheme)
        setContentView(binding.root)
        coins = try {
            SharePrefHelper.readString(userCoins)?.toInt()!!
        } catch (e: Exception) {
            0
        }
        binding.tvCoinCounter.text = "$coins"
        initAdapter()
        initListeners()
        initObservers()
        viewModel.getPurchasedThemes(this)
    }

    private fun initListeners(){
        binding.cvBuyCard.setOnClickListener {
            if(!purchasedThemeList.contains(selectedTheme)){
                if(coins >= 150) {
                    coins -= 150
                    SharePrefHelper.writeString(userCoins, "$coins")
                    binding.tvCoinCounter.text = "$coins"
                    viewModel.purchaseTheme(
                        this@SelectThemeActivity,
                        DBThemeModel(purchased_theme_Index = selectedTheme)
                    )
                    SharePrefHelper.writeInteger(selectedColorIndex, selectedTheme)
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                }else {
                    Toast.makeText(this, noCoinMsg, Toast.LENGTH_SHORT).show()
                }
            }else{
                SharePrefHelper.writeInteger(selectedColorIndex, selectedTheme)
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }

        }

        binding.backButton.setOnClickListener {
            finish()
        }

    }

    private fun initAdapter(){
        adapter = ThemeAdapter(this, object: ThemeAdapter.CallBacks{
            override fun onSelectColorEventCall(position: Int) {
                selectedTheme = position
                adapter.selectedTheme = position
                adapter.notifyDataSetChanged()
                updateTheme(position)
            }
        }, selectedTheme, arrayListOf())
        binding.rvTheme.adapter = adapter
    }

    private fun initObservers(){
        viewModel.purchasedThemeList.observe(this){
            purchasedThemeList = it
            adapter.purchasedList = purchasedThemeList
            adapter.notifyDataSetChanged()
        }
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
        binding.text1.setTextColor(ContextCompat.getColor(this, color))
        binding.cvBuyCard.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.premiumButton.setCardBackgroundColor(ContextCompat.getColor(this, color))
    }


}