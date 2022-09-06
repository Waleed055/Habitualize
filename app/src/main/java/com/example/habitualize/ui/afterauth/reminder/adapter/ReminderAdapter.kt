package com.example.habitualize.ui.afterauth.reminder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R

class ReminderAdapter(val context: Context) :
    RecyclerView.Adapter<ReminderAdapter.ViewHolderReminderAdapter>() {

    class ViewHolderReminderAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemLockImg = itemView.findViewById<ImageView>(R.id.item_lock_img)
        var itemReminderText = itemView.findViewById<TextView>(R.id.item_reminder_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderReminderAdapter {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_reminder,parent,false)
        return ViewHolderReminderAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderReminderAdapter, position: Int) {

        holder.itemLockImg.setOnClickListener {
            holder.itemLockImg.visibility = View.GONE
            holder.itemReminderText.visibility = View.VISIBLE
        }

    }

    override fun getItemCount() = 10

}