package com.example.habitualize.ui.selectlanguage

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivitySelectLanguageBinding
import com.example.habitualize.ui.splash.SplashActivity
import com.example.habitualize.utils.*
import java.util.*


class SelectLanguageActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLanguageBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        binding.includedLayout.tvTitle.text = "Languages"
        initListeners()
    }



    private fun initListeners(){
        binding.includedLayout.backButton.setOnClickListener {
            finish()
        }

        binding.cvEngLanguage.setOnClickListener {
            setLocale("en")
        }

        binding.cvArLanguage.setOnClickListener {
            setLocale("ar")
        }

        binding.cvDeLanguage.setOnClickListener {
            setLocale("de")
        }

        binding.cvFilLanguage.setOnClickListener {
            setLocale("fil")
        }

        binding.cvFrLanguage.setOnClickListener {
            setLocale("fr")
        }

        binding.cvHiLanguage.setOnClickListener {
            setLocale("hi")
        }

        binding.cvIdLanguage.setOnClickListener {
            setLocale("id")
        }

        binding.cvItLanguage.setOnClickListener {
            setLocale("it")
        }

        binding.cvJaLanguage.setOnClickListener {
            setLocale("ja")
        }

        binding.cvPtLanguage.setOnClickListener {
            setLocale("pt")
        }

        binding.cvRuLanguage.setOnClickListener {
            setLocale("ru")
        }

        binding.cvTrLanguage.setOnClickListener {
            setLocale("tr")
        }
    }

    private fun setLocale(langCode: String){
        SharePrefHelper.writeString(languageCode , langCode)
        SharePrefHelper.writeBoolean(isAppTasksLocal, false)
//        val myLocale = Locale(langCode)
//        val res: Resources = resources
//        val dm: DisplayMetrics = res.getDisplayMetrics()
//        val conf: Configuration = res.getConfiguration()
//        conf.locale = myLocale
//        res.updateConfiguration(conf, dm)

        var intent = Intent(this, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
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
    }
}