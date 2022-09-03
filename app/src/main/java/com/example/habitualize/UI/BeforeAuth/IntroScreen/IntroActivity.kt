package com.example.habitualize.UI.BeforeAuth.IntroScreen

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.habitualize.R
import com.example.habitualize.UI.AfterAuth.HomeActivity.HomeActivity
import com.example.habitualize.UI.BeforeAuth.IntroScreen.Adapter.ViewPagerAdapter
import com.google.android.material.card.MaterialCardView
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator


class IntroActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var introNextBtn: TextView
    lateinit var introBoldText: TextView
    lateinit var introDescriptionText: TextView
    lateinit var intoCardView : MaterialCardView
    lateinit var dotsIndicator : DotsIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        initViews()
        initAdapter()
        initListeners()

    }

    private fun initViews() {
        viewPager = findViewById(R.id.view_pager)
        introBoldText = findViewById(R.id.intro_bold_text)
        introDescriptionText = findViewById(R.id.intro_description_text)
        introNextBtn = findViewById(R.id.intro_next_btn)
        intoCardView = findViewById(R.id.into_card_view)
        intoCardView.setBackgroundResource(R.drawable.intro_screen_background)
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
                        introBoldText.text = getString(R.string.intro_3_bold_text)
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
                }
            }
        }

    }

    private fun initAdapter() {
        val viewPagerAdapter = ViewPagerAdapter(this,listOf(R.drawable.intor1_gif, R.drawable.intor2_gif, R.drawable.intor3_gif))
        viewPager.adapter = viewPagerAdapter
        dotsIndicator.attachTo(viewPager)
    }
}