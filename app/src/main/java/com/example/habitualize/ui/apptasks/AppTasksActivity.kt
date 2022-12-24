package com.example.habitualize.ui.apptasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityAppTasksBinding
import com.example.habitualize.managers.AdsManager
import com.example.habitualize.ui.apptasks.adapter.AppTasksAdapter
import com.example.habitualize.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppTasksActivity : AppCompatActivity() {
    private val viewModel: AppTaskViewModel by viewModels()
    private lateinit var binding: ActivityAppTasksBinding
    private lateinit var adapter: AppTasksAdapter
    private var whichScreen = 0
    private var coins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppTasksBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        coins = try { SharePrefHelper.readString(userCoins)?.toInt()!! }catch (e: Exception){ 0 }

        initAdapter()
        initObserver()
        initListeners()
        if(!SharePrefHelper.readBoolean(isAppTasksLocal)){
            viewModel.insertAppTasks(this, SharePrefHelper.readString(languageCode).toString())
        }else{
            viewModel.getAppTasksList(this)
        }
    }

    private fun initListeners(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun initObserver(){
        viewModel.userAppTaskList.observe(this){
            adapter.taskList = it
            adapter.notifyDataSetChanged()
        }
    }



    private fun initAdapter(){
        adapter = AppTasksAdapter(this, object: AppTasksAdapter.CallBacks{
            override fun onGetRewardEvent(task_id: Long, reward: Int) {
                viewModel.completeTask(this@AppTasksActivity, task_id)
                coins += reward
                SharePrefHelper.writeString(userCoins,"$coins")
                Toast.makeText(
                    this@AppTasksActivity,
                    "${resources.getString(R.string.you_have_received)} $reward ${resources.getString(R.string.coins)}",
                    Toast.LENGTH_SHORT
                ).show()
                AdsManager.getInstance().showInterstitial(this@AppTasksActivity)
            }
        },SharePrefHelper.readInteger(selectedColorIndex))
        binding.rvAppTask.adapter = adapter
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
    }

}