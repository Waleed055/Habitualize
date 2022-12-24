package com.example.habitualize.ui.feed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityFeedBinding
import com.example.habitualize.ui.feed.adapter.FeedAdapter
import com.example.habitualize.ui.feed.createfeed.CreateFeedActivity
import com.example.habitualize.ui.models.FeedModel
import com.example.habitualize.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedActivity : AppCompatActivity() {
    private val viewModel: FeedViewModel by viewModels()
    lateinit var binding : ActivityFeedBinding
    lateinit var feedAdapter: FeedAdapter
    private var feedList = arrayListOf<FeedModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)

        initAdapter()
        initListeners()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllFeedsByLanguageCode(
            this, SharePrefHelper.readString(languageCode).toString()
        )
    }

    private fun initObserver(){
        viewModel.feedList.observe(this){
            if(it.isEmpty()){
                binding.dataLayout.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
                return@observe
            }else{
                binding.dataLayout.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
            }
            feedList.clear()
            feedList.addAll(it)
            feedAdapter.feedList = feedList
            feedAdapter.notifyDataSetChanged()
        }
    }

    private fun initAdapter() {
        feedAdapter = FeedAdapter(this, feedList, object: FeedAdapter.CallBacks{
            override fun onDeleteEvent(feed: FeedModel, position: Int) {
                viewModel.deleteFeed(this@FeedActivity, feed.feed_id)
                feedList.removeAt(position)
                feedAdapter.notifyItemRemoved(position)
                feedAdapter.notifyItemRangeChanged(position, feedAdapter.itemCount)
            }
        })
        binding.feedRv.adapter = feedAdapter
    }

    private fun initListeners(){
        binding.cvAddBtn.setOnClickListener {
            startActivity(Intent(this,CreateFeedActivity::class.java))
        }

        binding.backButton.setOnClickListener {
            finish()
        }

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
        binding.cvAddBtn.setCardBackgroundColor(ContextCompat.getColor(this, color))
    }
}