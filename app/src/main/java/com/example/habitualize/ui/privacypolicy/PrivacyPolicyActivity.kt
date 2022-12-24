package com.example.habitualize.ui.privacypolicy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityPrivacyPolicyBinding
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.changeStatusBarColor
import com.example.habitualize.utils.selectedColorIndex
import org.json.JSONObject

class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacyPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        binding.includedLayout.tvTitle.text = resources.getString(R.string.privacy_policy)
        binding.includedLayout.backButton.setOnClickListener {
            finish()
        }
        val bufferReader = application.assets.open("privacypolicy.json").bufferedReader()
        val data = bufferReader.use {
            it.readText()
        }
        val obj = JSONObject(data)
        binding.tvPrivacyPolicy.text = obj.getString("text")
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