package com.example.habitualize.ui.reminder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.example.habitualize.ui.models.AllRemindersModel

class ReminderAdapter(
    val context: Context,
    var colorPosition: Int,
    val callBacks: CallBacks,
    var langCode: String
): RecyclerView.Adapter<ReminderAdapter.MyViewHolder>() {
    var allReminders = AllRemindersModel()


    interface CallBacks {
        fun onOpenEventCall(reminder: String, langCode: String, isOpened: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = allReminders.all_reminders[position]
        var openedReminders = allReminders.opened_reminders
        holder.card.setCardBackgroundColor(getColor())
        holder.item_reminder_text.text = item
        for(openReminder in openedReminders){
            if(item == openReminder.reminder && langCode == openReminder.language_code){
                holder.item_reminder_text.visibility = View.VISIBLE
                holder.item_lock_img.visibility = View.GONE
                break
            }else if(item != openReminder.reminder){
                holder.item_reminder_text.visibility = View.GONE
                holder.item_lock_img.visibility = View.VISIBLE
            }
        }

        holder.itemView.setOnClickListener {
            if(holder.item_lock_img.visibility == View.VISIBLE){
                callBacks.onOpenEventCall(item, langCode,false)
            }else{
                callBacks.onOpenEventCall(item, langCode,true)
            }
        }
    }

    override fun getItemCount(): Int {
        return allReminders.all_reminders.size
    }

    private fun getColor(): Int {
        return when(colorPosition){
            0->{
                ContextCompat.getColor(context, R.color.theme_light)
            }
            1->{
                ContextCompat.getColor(context, R.color.theme_2_light)
            }
            2->{
                ContextCompat.getColor(context, R.color.theme_3_light)
            }
            3->{
                ContextCompat.getColor(context, R.color.theme_4_light)
            }
            4->{
                ContextCompat.getColor(context, R.color.theme_5_light)
            }
            5->{
                ContextCompat.getColor(context, R.color.theme_6_light)
            }
            6->{
                ContextCompat.getColor(context, R.color.theme_7_light)
            }
            7->{
                ContextCompat.getColor(context, R.color.theme_8_light)
            }
            else->{
                ContextCompat.getColor(context, R.color.theme_light)
            }
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_lock_img: ImageView = itemView.findViewById(R.id.item_lock_img)
        val item_reminder_text: TextView = itemView.findViewById(R.id.item_reminder_text)
        val card: CardView = itemView.findViewById(R.id.card)
    }
}