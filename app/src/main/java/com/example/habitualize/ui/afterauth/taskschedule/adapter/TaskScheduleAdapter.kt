package com.example.habitualize.ui.afterauth.taskschedule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R

class TaskScheduleAdapter(val context: Context, val callBacks: CallBacks) : RecyclerView.Adapter<TaskScheduleAdapter.ViewHolderDaysAdapter>()  {

    interface CallBacks{
        fun onClickTask()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDaysAdapter {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_challenge,parent,false)
        return ViewHolderDaysAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDaysAdapter, position: Int) {
        if(position <= 15){
            holder.challenge_card.setCardBackgroundColor(ContextCompat.getColor(context,R.color.theme))
            holder.challenge_text.setTextColor(ContextCompat.getColor(context,R.color.white))
        }else{
            holder.challenge_card.setCardBackgroundColor(ContextCompat.getColor(context,R.color.white))
            holder.challenge_text.setTextColor(ContextCompat.getColor(context,R.color.black))
        }

        holder.itemView.setOnClickListener {
            callBacks.onClickTask()
        }

    }

    override fun getItemCount() = 30

    class ViewHolderDaysAdapter(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val challenge_card : CardView = itemView.findViewById(R.id.challenge_card)
        val challenge_text : TextView = itemView.findViewById(R.id.challenge_text)
    }

}