package com.example.habitualize.ui.splash

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivitySplashBinding
import com.example.habitualize.ui.auth.login.LoginActivity
import com.example.habitualize.ui.home.HomeActivity
import com.example.habitualize.ui.selectlanguage.SelectLanguageActivity
import com.example.habitualize.utils.*
import java.util.*


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(SharePrefHelper.readString(languageCode).orEmpty())
        checkTheme()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        var counter = SharePrefHelper.readInteger(openAppCounterForRateUs)
        SharePrefHelper.writeInteger(openAppCounterForRateUs,++counter)
        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                    launchActivity()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
        background.start()
    }

    private fun launchActivity(){
        if (!SharePrefHelper.readBoolean(isLanguageScreenShow)){
            SharePrefHelper.writeBoolean(isLanguageScreenShow, true)
            val intent = Intent(this@SplashActivity, SelectLanguageActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Locale.setDefault(Locale(SharePrefHelper.readString(languageCode)))
            if(SharePrefHelper.readBoolean(isLogIn)){
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun checkTheme(){
        val isDarkModeOn = SharePrefHelper.readBoolean("isDarkModeOn")
        if (isDarkModeOn) {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
        }else{
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
        }
    }

    private fun setLocale(langCode: String){
        SharePrefHelper.writeString(languageCode , langCode)
        val myLocale = Locale(langCode)
        val res: Resources = resources
        val dm: DisplayMetrics = res.getDisplayMetrics()
        val conf: Configuration = res.getConfiguration()
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
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
        binding.gif.setColorFilter(ContextCompat.getColor(this, color))
        binding.text.setTextColor(ContextCompat.getColor(this, color))
    }


}