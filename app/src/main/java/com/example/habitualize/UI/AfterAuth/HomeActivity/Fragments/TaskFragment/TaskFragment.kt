package com.example.habitualize.UI.AfterAuth.HomeActivity.Fragments.TaskFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.example.habitualize.UI.AfterAuth.HomeActivity.Fragments.TaskFragment.Adapter.TaskAdapter

class TaskFragment : Fragment() {


    lateinit var taskAdapter: TaskAdapter
    lateinit var taskRv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_task, container, false)


        initViews(view)
        initAdapter()

        return view
    }

    private fun initAdapter() {
        taskAdapter = TaskAdapter(requireContext())
        taskRv.adapter = taskAdapter
    }

    private fun initViews(view: View) {
        taskRv = view.findViewById(R.id.task_rv)
    }

}