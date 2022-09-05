package com.example.habitualize.ui.afterauth.home

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityHomeBinding
import com.example.habitualize.ui.afterauth.home.fragments.ChatFragment
import com.example.habitualize.ui.afterauth.home.fragments.ProfileFragment
import com.example.habitualize.ui.afterauth.home.fragments.taskfragment.TaskFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.zagori.bottomnavbar.BottomNavBar


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
                R.id.nav_blog -> {

                }
                R.id.nav_community -> {

                }
                R.id.nav_share_with_friend -> {

                }
                R.id.nav_reminder -> {

                }
                R.id.nav_contact_us -> {

                }
                R.id.nav_log_out -> {

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
                    setCurrentFragment(ChatFragment())
                }
                R.id.nav_add_task -> {

                }
                R.id.nav_profile -> {
                    setCurrentFragment(ProfileFragment())
                }
            }
            true
        }

        binding.includedLayout.fab.setOnClickListener { view ->
            setCurrentFragment(TaskFragment())
            binding.includedLayout.bottomNavigationView.menu.setGroupCheckable(0, false, true)
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