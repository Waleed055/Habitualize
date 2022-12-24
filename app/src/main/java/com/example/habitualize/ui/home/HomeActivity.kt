package com.example.habitualize.ui.home

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityHomeBinding
import com.example.habitualize.ui.apptasks.AppTasksActivity
import com.example.habitualize.ui.auth.login.LoginActivity
import com.example.habitualize.ui.auth.signup.SignUpActivity
import com.example.habitualize.ui.challenge.ChallengeActivity
import com.example.habitualize.ui.communitychallenges.CommunityChallengesActivity
import com.example.habitualize.ui.feed.FeedActivity
import com.example.habitualize.ui.gratitude.GratitudeActivity
import com.example.habitualize.ui.hidentask.HiddenTaskActivity
import com.example.habitualize.ui.home.taskdetail.TaskDetailActivity
import com.example.habitualize.ui.home.viewmodels.HomeViewModel
import com.example.habitualize.ui.models.DailyChallengesModel
import com.example.habitualize.ui.notification.NotificationActivity
import com.example.habitualize.ui.premium.PremiumActivity
import com.example.habitualize.ui.privacypolicy.PrivacyPolicyActivity
import com.example.habitualize.ui.profile.ProfileActivity
import com.example.habitualize.ui.reminder.ReminderActivity
import com.example.habitualize.ui.selectlanguage.SelectLanguageActivity
import com.example.habitualize.ui.splash.SplashActivity
import com.example.habitualize.ui.theme.SelectThemeActivity
import com.example.habitualize.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var binding: ActivityHomeBinding
    var rate_us_dialog: Dialog? = null
    var ivCancelIcon: ImageView? = null
    var cvRateUsBtn: CardView? = null

    companion object{
        var blogLink = ""
        var musicLink = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(SharePrefHelper.readString(languageCode).orEmpty())
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        setSupportActionBar(binding.includedLayout.toolbar)
        initListeners()
        setBottomNavigationView()
        initObservers()
        if(SharePrefHelper.readInteger(openAppCounterForRateUs) > 7){
            SharePrefHelper.writeInteger(openAppCounterForRateUs, 0)
            rateUsDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        setData()
    }

    private fun setData() {
        binding.drawerIncludedLayout.smDarkMode.isChecked =
            SharePrefHelper.readBoolean("isDarkModeOn")
        Glide.with(this)
            .load(SharePrefHelper.readString(userImage))
            .into(binding.drawerIncludedLayout.imageView)
        binding.drawerIncludedLayout.tvUserName.text = SharePrefHelper.readString(userName)
        binding.drawerIncludedLayout.tvCoinsCounter.text = SharePrefHelper.readString(userCoins)
    }

    private fun initObservers(){
        viewModel.isLogOut.observe(this){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun initListeners() {
        binding.drawerIncludedLayout.llPremium.setOnClickListener {
            binding.drawerLayout.close()
            startActivity(
                Intent(
                    this,
                    PremiumActivity::class.java
                )
            )
        }
        binding.drawerIncludedLayout.llBlog.setOnClickListener {
            binding.drawerLayout.close()
            try {
                val uri = Uri.parse(HomeActivity.blogLink)
                val i = Intent(Intent.ACTION_VIEW)
                i.data = uri
                startActivity(i)
            }catch (e: Exception){
                Toast.makeText(this, resources.getString(R.string.not_found), Toast.LENGTH_SHORT).show()
            }
        }
        binding.drawerIncludedLayout.llRelaxingMusic.setOnClickListener {
            binding.drawerLayout.close()
            try {
                val uri = Uri.parse(HomeActivity.musicLink)
                val i = Intent(Intent.ACTION_VIEW)
                i.data = uri
                startActivity(i)
            }catch (e: Exception){
                Toast.makeText(this, resources.getString(R.string.not_found), Toast.LENGTH_SHORT).show()
            }
        }
        binding.drawerIncludedLayout.llGratitude.setOnClickListener {
            binding.drawerLayout.close()
            startActivity(
                Intent(
                    this,
                    GratitudeActivity::class.java
                )
            )
        }
        binding.drawerIncludedLayout.llNotifications.setOnClickListener {
            binding.drawerLayout.close()
            startActivity(
                Intent(
                    this,
                    NotificationActivity::class.java
                )
            )
        }
        binding.drawerIncludedLayout.llCommunityChallenges.setOnClickListener {
            binding.drawerLayout.close()
            startActivity(
                Intent(
                    this,
                    CommunityChallengesActivity::class.java
                )
            )
        }
        binding.drawerIncludedLayout.llShare.setOnClickListener {
            binding.drawerLayout.close()
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "${resources.getString(R.string.share_app_text)}https://play.google.com/store/apps/details?id=$packageName"
            )
            startActivity(Intent.createChooser(intent, "choose one"))
            //----------------------------------------------------------------------
            //counter
            var _counter = SharePrefHelper.readInteger(shareAppCounter) + 1
            SharePrefHelper.writeInteger(shareAppCounter, _counter)


        }
        binding.drawerIncludedLayout.llReminder.setOnClickListener {
            binding.drawerLayout.close()
            startActivity(Intent(this, ReminderActivity::class.java))
        }
        binding.drawerIncludedLayout.llContactUs.setOnClickListener {
            binding.drawerLayout.close()
//            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
            privacyPolicyEvent()
        }

        binding.drawerIncludedLayout.smDarkMode.setOnClickListener {
            binding.drawerLayout.close()
            if (binding.drawerIncludedLayout.smDarkMode.isChecked) {
                //theme change
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
                SharePrefHelper.writeBoolean("isDarkModeOn", true)
            } else {
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
                SharePrefHelper.writeBoolean("isDarkModeOn", false)
            }
//            var intent = Intent(this, SplashActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//            finish()
        }

        binding.drawerIncludedLayout.llChangeTheme.setOnClickListener {
            binding.drawerLayout.close()
            startActivity(Intent(this, SelectThemeActivity::class.java))
        }

        binding.drawerIncludedLayout.llChangeLanguage.setOnClickListener {
            binding.drawerLayout.close()
            startActivity(Intent(this, SelectLanguageActivity::class.java))
        }

        binding.drawerIncludedLayout.llAppTasks.setOnClickListener {
            binding.drawerLayout.close()
            var intent = Intent(this, AppTasksActivity::class.java)
            startActivity(intent)
        }

        binding.drawerIncludedLayout.llHiddenTasks.setOnClickListener {
            binding.drawerLayout.close()
            var intent = Intent(this, HiddenTaskActivity::class.java)
            startActivity(intent)
        }

        binding.drawerIncludedLayout.llLogout.setOnClickListener {
            binding.drawerLayout.close()
            viewModel.deleteAllData(this)
        }
    }

    private fun setBottomNavigationView() {
        binding.includedLayout.bottomNavigationView.background = null
        binding.includedLayout.bottomNavigationView.menu.getItem(1).isEnabled = false
        setCurrentFragment(TaskFragment())
        binding.includedLayout.bottomNavigationView.menu.setGroupCheckable(0, false, true)
        binding.includedLayout.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_feed -> {
                    startActivity(Intent(this, FeedActivity::class.java))
                }
                R.id.nav_add_task -> {}
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
            }
            true
        }

        binding.includedLayout.fab.setOnClickListener { view ->
            binding.includedLayout.bottomNavigationView.menu.setGroupCheckable(0, false, true)
            var intent = Intent(this, ChallengeActivity::class.java)
            intent.putExtra("whichScreen", 0)
            startActivity(intent)
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.host_framlayout, fragment)
            commit()
        }


    fun openDrawer() {
        binding.drawerLayout.open()
    }

    private fun updateTheme(position: Int) {
        when (position) {
            0 -> {
                setThemeColor(R.color.theme, R.color.theme_light)
            }
            1 -> {
                setThemeColor(R.color.theme_2, R.color.theme_2_light)
            }
            2 -> {
                setThemeColor(R.color.theme_3, R.color.theme_3_light)
            }
            3 -> {
                setThemeColor(R.color.theme_4, R.color.theme_4_light)
            }
            4 -> {
                setThemeColor(R.color.theme_5, R.color.theme_5_light)
            }
            5 -> {
                setThemeColor(R.color.theme_6, R.color.theme_6_light)
            }
            6 -> {
                setThemeColor(R.color.theme_7, R.color.theme_7_light)
            }
            7 -> {
                setThemeColor(R.color.theme_8, R.color.theme_8_light)
            }
        }
    }

    private fun setThemeColor(color: Int, lightColor: Int) {
        changeStatusBarColor(this, color)
        //rate us dialog

        ivCancelIcon?.setColorFilter(ContextCompat.getColor(this, color))
        cvRateUsBtn?.setCardBackgroundColor(ContextCompat.getColor(this, color))

        //drawer
        binding.drawerIncludedLayout.root.setBackgroundColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.ivPremium.setColorFilter(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.ivBlog.setColorFilter(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.ivGratitude.setColorFilter(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.ivHiddenTask.setColorFilter(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.ivNotification.setColorFilter(
            ContextCompat.getColor(
                this,
                color
            )
        )
        binding.drawerIncludedLayout.ivCommunity.setColorFilter(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.ivShare.setColorFilter(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.ivReminder.setColorFilter(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.ivContactus.setColorFilter(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.ivAppTask.setColorFilter(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.ivRelaxingMusic.setColorFilter(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.ivChangeTheme.setColorFilter(
            ContextCompat.getColor(
                this,
                color
            )
        )
        binding.drawerIncludedLayout.ivChangeLang.setColorFilter(
            ContextCompat.getColor(
                this,
                color
            )
        )
        binding.drawerIncludedLayout.ivLogout.setColorFilter(ContextCompat.getColor(this, color))

        binding.drawerIncludedLayout.tvPremium.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvBlog.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvGratitude.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvHiddenTask.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvNotification.setTextColor(
            ContextCompat.getColor(
                this,
                color
            )
        )
        binding.drawerIncludedLayout.tvCommunity.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvShare.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvReminder.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvContactus.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvAppTask.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvDarkMood.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvChangeTheme.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvChangeLang.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvLogout.setTextColor(ContextCompat.getColor(this, color))
        binding.drawerIncludedLayout.tvRelaxingMusic.setTextColor(ContextCompat.getColor(this, color))


        //main layout
        binding.drawerIncludedLayout.llPremium.setBackgroundColor(
            ContextCompat.getColor(
                this,
                lightColor
            )
        )
        binding.includedLayout.fab.setBackgroundTintList(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    this,
                    color
                )
            )
        );
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

    private fun rateUsDialog() {
        rate_us_dialog = Dialog(this)
        rate_us_dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rate_us_dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        rate_us_dialog?.setContentView(R.layout.item_rate_us)
        ivCancelIcon = rate_us_dialog?.findViewById(R.id.ivCancelIcon)
        cvRateUsBtn = rate_us_dialog?.findViewById(R.id.cvRateUsBtn)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))

        ivCancelIcon?.setOnClickListener {
            rate_us_dialog?.dismiss()
        }

        cvRateUsBtn?.setOnClickListener {
            rate_us_dialog?.dismiss()
            Handler(Looper.myLooper()!!).postDelayed({rateUsEvent()},500)
        }

        rate_us_dialog?.show()
    }

    private fun rateUsEvent() {
        try{
            val url: String = "https://play.google.com/store/apps/details?id=$packageName"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "App is not Installed to open this link!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun privacyPolicyEvent(){
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://habitualizerapp.com/privacy-policy/")
        )
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }else{
            Toast.makeText(this, "App is not Installed to open this link!", Toast.LENGTH_SHORT).show()
        }
    }

}