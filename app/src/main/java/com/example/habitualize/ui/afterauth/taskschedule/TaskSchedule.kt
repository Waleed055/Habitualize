package com.example.habitualize.ui.afterauth.taskschedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habitualize.databinding.ActivityTaskSheduleBinding
import com.example.habitualize.ui.afterauth.singleday.SingleDayActivity
import com.example.habitualize.ui.afterauth.taskschedule.adapter.TaskScheduleAdapter

class TaskSchedule : AppCompatActivity(), TaskScheduleAdapter.CallBacks {

    lateinit var binding: ActivityTaskSheduleBinding
    lateinit var taskScheduleAdapter: TaskScheduleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskSheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initListeners()
        menuListeners()

    }

    private fun initListeners(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        taskScheduleAdapter = TaskScheduleAdapter(this,this)
        binding.taskRecyclerview.adapter = taskScheduleAdapter
    }

    private fun menuListeners() {

        binding.circleMenu.setOnItemClickListener { menuButton ->
            when (menuButton) {
                0 -> {}
                1 -> {

                }
                2 -> {}
            }
        }
    }

    override fun onClickTask() {
        startActivity(Intent(this,SingleDayActivity::class.java))
    }
}