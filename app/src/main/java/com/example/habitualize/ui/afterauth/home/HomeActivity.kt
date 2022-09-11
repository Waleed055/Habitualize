package com.example.habitualize.ui.afterauth.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityHomeBinding
import com.example.habitualize.ui.afterauth.communitychallenges.CommunityChallengesActivity
import com.example.habitualize.ui.afterauth.challenge.ChallengeActivity
import com.example.habitualize.ui.afterauth.feed.FeedActivity
import com.example.habitualize.ui.afterauth.notification.NotificationActivity
import com.example.habitualize.ui.afterauth.premium.PremiumActivity
import com.example.habitualize.ui.afterauth.profile.ProfileActivity
import com.example.habitualize.ui.afterauth.reminder.ReminderActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var binding : ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.includedLayout.toolbar)
        setSideDrawer()
        setBottomNavigationView()
    }


    private fun setSideDrawer(){
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_blog,
                R.id.nav_community,
                R.id.nav_share_with_friend,
                R.id.nav_reminder,
                R.id.nav_contact_us,
                R.id.nav_log_out
            ), binding.drawerLayout
        )

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_premium -> {
                    startActivity(Intent(this,PremiumActivity::class.java))
                }
                R.id.nav_blog -> {

                }
                R.id.nav_notification -> {
                    startActivity(Intent(this,NotificationActivity::class.java))
                }
                R.id.nav_community -> {
                    startActivity(Intent(this, CommunityChallengesActivity::class.java))
                }
                R.id.nav_share_with_friend -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, "Check this out! Habitulizer is an amazing app: [this will be playStore link]")
                    startActivity(Intent.createChooser(intent, "choose one"))
                }
                R.id.nav_reminder -> {
                    startActivity(Intent(this,ReminderActivity::class.java))
                }
                R.id.nav_contact_us -> {

                }
                R.id.nav_log_out -> {
                    finish()
                }
            }
            true
        }
    }

    private fun setBottomNavigationView(){
        binding.includedLayout.bottomNavigationView.background = null
        binding.includedLayout.bottomNavigationView.menu.getItem(1).isEnabled = false
        setCurrentFragment(TaskFragment())
        binding.includedLayout.bottomNavigationView.menu.setGroupCheckable(0, false, true)
        binding.includedLayout.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.nav_feed -> {
                    startActivity(Intent(this,FeedActivity::class.java))
                }
                R.id.nav_add_task -> {}
                R.id.nav_profile -> {
                    startActivity(Intent(this,ProfileActivity::class.java))
                }
            }
            true
        }

        binding.includedLayout.fab.setOnClickListener { view ->
            binding.includedLayout.bottomNavigationView.menu.setGroupCheckable(0, false, true)
            startActivity(Intent(this,ChallengeActivity::class.java))
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.host_framlayout, fragment)
            commit()
        }


    fun openDrawer(){
        binding.drawerLayout.open()
    }

}