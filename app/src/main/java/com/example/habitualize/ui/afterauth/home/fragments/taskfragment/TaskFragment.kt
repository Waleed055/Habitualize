package com.example.habitualize.ui.afterauth.home.fragments.taskfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.example.habitualize.R
import com.example.habitualize.ui.afterauth.home.HomeActivity
import com.example.habitualize.ui.afterauth.home.fragments.taskfragment.Adapter.TaskAdapter

class TaskFragment : Fragment() {

    lateinit var task_parent : FrameLayout
    lateinit var taskDrawerOpenBtn : ImageView
    lateinit var taskAdapter: TaskAdapter
    lateinit var taskRv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_task, container, false)


        initViews(view)
        initAdapter()
        initListeners()

        return view
    }

    private fun initListeners() {
        taskDrawerOpenBtn.setOnClickListener {
            (activity as HomeActivity).drawerLayout.open()
        }
    }

    private fun initAdapter() {
        taskAdapter = TaskAdapter(requireContext())
        taskRv.adapter = taskAdapter
    }

    private fun initViews(view: View) {
        task_parent = view.findViewById(R.id.task_parent)
        taskDrawerOpenBtn = view.findViewById(R.id.task_drawer_open_btn)
        taskRv = view.findViewById(R.id.task_rv)


    }

}