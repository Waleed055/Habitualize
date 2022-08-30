package com.example.habitualize.UI.BeforeAuth.IntroScreen

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.habitualize.R
import com.example.habitualize.UI.AfterAuth.HomeActivity.HomeActivity
import com.example.habitualize.UI.BeforeAuth.IntroScreen.Adapter.ViewPagerAdapter


class IntroActivity : AppCompatActivity() {

    lateinit var view_pager: ViewPager2
    lateinit var intro_next_btn: TextView
    lateinit var intro_bold_text: TextView
    lateinit var intro_description_text: TextView
    var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val gifList = listOf(R.drawable.intor1_gif, R.drawable.intor2_gif, R.drawable.intor3_gif)

        view_pager = findViewById(R.id.view_pager)
        intro_bold_text = findViewById(R.id.intro_bold_text)
        intro_description_text = findViewById(R.id.intro_description_text)
        intro_next_btn = findViewById(R.id.intro_next_btn)


        val viewPagerAdapter = ViewPagerAdapter(this, gifList)
        view_pager.adapter = viewPagerAdapter


        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                when (position) {
                    0 -> {
//                        view_pager.setCurrentItem(1, true)
                        intro_bold_text.text = "Are you excited?"
                        intro_description_text.text = "This smart tool is designed to help you better manage your tasks!"
                    }
                    1 -> {
//                        view_pager.setCurrentItem(2, true)
                        intro_bold_text.text = "Click on the bell"
                        intro_description_text.text = "And choose the time to be notified"                }
                    2 -> {
                        intro_bold_text.text = "Welcome, Kashif!"
                        intro_description_text.text =
                            "It is estimated that it takes people 21 days to form a new habit. So it's a perfect amount of time to change or introduce something new in your life."
                    }
                }

            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }


        })

        intro_next_btn.setOnClickListener {
            when(view_pager.currentItem)
            {
                0->
                {
                    view_pager.setCurrentItem(1,true)
                }
                1->
                {
                    view_pager.setCurrentItem(2,true)
                }
                2->
                {
                    startActivity(Intent(this,HomeActivity::class.java))
                }
            }
        }

    }
}