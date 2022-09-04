package com.example.habitualize.ui.afterauth.home.fragments.taskfragment.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R

class TaskAdapter(val context: Context) : RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder>()  {

    class TaskAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)

        return TaskAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {
    }

    override fun getItemCount() = 4

}