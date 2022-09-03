package com.example.habitualize.UI.AfterAuth.HomeActivity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.habitualize.R
import com.zagori.bottomnavbar.BottomNavBar


class HomeActivity : AppCompatActivity() {

    lateinit var userDrawer : DrawerLayout
    lateinit var bottom_nav_view : BottomNavBar
    private var navHostFragment: NavHostFragment? = null
    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setUpNavBar()


    }

    private fun initViews() {
        userDrawer = findViewById(R.id.user_drawer)
    }

    

    fun setUpNavBar() {
        bottom_nav_view = findViewById(R.id.bottom_nav_view)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.home_fragment_container) as NavHostFragment
        navController = navHostFragment!!.navController

        bottom_nav_view.setBottomNavigationListener(object : BottomNavBar.OnBottomNavigationListener
        {
            override fun onNavigationItemSelected(menuItem: MenuItem?): Boolean {
                navController = Navigation.findNavController(this@HomeActivity, R.id.home_fragment_container)
                when (menuItem?.itemId) {
                    R.id.chatFragment -> navController.navigate(R.id.chatFragment)
                    R.id.taskFragment -> navController.navigate(R.id.taskFragment)
                    R.id.profileFragment -> navController.navigate(R.id.profileFragment)
                }
                return true
            }

        })

    }
}