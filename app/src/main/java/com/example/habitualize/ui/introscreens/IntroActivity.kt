package com.example.habitualize.ui.introscreens

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.habitualize.R
import com.example.habitualize.ui.home.HomeActivity
import com.example.habitualize.ui.introscreens.Adapter.ViewPagerAdapter
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.changeStatusBarColor
import com.example.habitualize.utils.selectedColorIndex
import com.example.habitualize.utils.userName
import com.google.android.material.card.MaterialCardView
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator


class IntroActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var introNextBtn: CardView
    lateinit var introBoldText: TextView
    lateinit var introDescriptionText: TextView
    lateinit var dotsIndicator : DotsIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        initViews()
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        initAdapter()
        initListeners()

    }

    private fun initViews() {
        viewPager = findViewById(R.id.view_pager)
        introBoldText = findViewById(R.id.intro_bold_text)
        introDescriptionText = findViewById(R.id.intro_description_text)
        introNextBtn = findViewById(R.id.intro_next_btn)
        dotsIndicator = findViewById(R.id.dots_indicator)
    }

    private fun initListeners() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                when (position) {
                    0 -> {
                        introBoldText.text = getString(R.string.intro_1_bold_text)
                        introDescriptionText.text = getString(R.string.intro_1_dec_text)
                    }
                    1 -> { introBoldText.text = getString(R.string.intro_2_bold_text)
                        introDescriptionText.text = getString(R.string.intro_2_dec_text)                }
                    2 -> {
                        introBoldText.text = "${getString(R.string.intro_3_bold_text)} ${SharePrefHelper.readString(userName)}"
                        introDescriptionText.text =
                            getString(R.string.intro_3_dec_text)
                    }
                }

            }
            override fun onPageSelected(position: Int) { super.onPageSelected(position) }
            override fun onPageScrollStateChanged(state: Int) { super.onPageScrollStateChanged(state) }
        })

        introNextBtn.setOnClickListener {
            when(viewPager.currentItem)
            {
                0->
                {
                    viewPager.setCurrentItem(1,true)
                }
                1->
                {
                    viewPager.setCurrentItem(2,true)
                }
                2->
                {
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
            }
        }

    }

    private fun initAdapter() {
        val viewPagerAdapter = ViewPagerAdapter(this,listOf(R.drawable.intor1_gif, R.drawable.intor2_gif, R.drawable.intor3_gif))
        viewPager.adapter = viewPagerAdapter
        dotsIndicator.attachTo(viewPager)
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
        dotsIndicator.selectedDotColor = ContextCompat.getColor(this, color)
        introNextBtn.setCardBackgroundColor(ContextCompat.getColor(this, color))
    }
}