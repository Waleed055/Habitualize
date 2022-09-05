package com.example.habitualize.ui.afterauth.home.fragments.taskfragment.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.example.habitualize.ui.afterauth.home.fragments.taskfragment.taskInterface.TaskItemListener

class TaskAdapter(val context: Context, val taskItemListener : TaskItemListener) : RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder>()  {

    class TaskAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        return TaskAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            taskItemListener.onItemClicked(position)
        }

    }

    override fun getItemCount() = 4

}