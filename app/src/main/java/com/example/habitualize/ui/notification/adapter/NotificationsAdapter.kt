package com.example.habitualize.ui.notification.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.example.habitualize.ui.notification.NotificationModel

class NotificationsAdapter(
    val context: Context,
    val callBacks: CallBacks,
    var colorPosition: Int,
    var notificationList: ArrayList<NotificationModel>
): RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>(){
    interface CallBacks{
        fun onNotificationDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_notifications, parent, false))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        var item = notificationList[position].time.split(":")
        holder.tv_time.text = "${item[0]}:${item[1]}"
        holder.tv_am_pm.text = "${item[2]}"
        holder.tv_notification_index.text = "${context.resources.getString(R.string.notification)} #${position+1}"
        holder.tv_notification_index.setTextColor(getColor(false))
        holder.tv_time.setTextColor(getColor(false))
        holder.tv_am_pm.setTextColor(getColor(false))
        holder.cv_am_pm_card.setCardBackgroundColor(getColor(true))
        holder.cv_time_card.setCardBackgroundColor(getColor(true))
        holder.iv_remove.setOnClickListener { callBacks.onNotificationDelete(position) }

    }

    override fun getItemCount() = notificationList.size

    private fun getColor(isLight: Boolean): Int {
        return if(!isLight){
            when(colorPosition){
                0->{
                    ContextCompat.getColor(context, R.color.theme)
                }
                1->{
                    ContextCompat.getColor(context, R.color.theme_2)
                }
                2->{
                    ContextCompat.getColor(context, R.color.theme_3)
                }
                3->{
                    ContextCompat.getColor(context, R.color.theme_4)
                }
                4->{
                    ContextCompat.getColor(context, R.color.theme_5)
                }
                5->{
                    ContextCompat.getColor(context, R.color.theme_6)
                }
                6->{
                    ContextCompat.getColor(context, R.color.theme_7)
                }
                7->{
                    ContextCompat.getColor(context, R.color.theme_8)
                }
                else->{
                    ContextCompat.getColor(context, R.color.theme)
                }
            }
        }else {
            when(colorPosition){
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
    }

    inner class NotificationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tv_notification_index: TextView = itemView.findViewById(R.id.tv_notification_index)
        val tv_time: TextView = itemView.findViewById(R.id.tv_time)
        val tv_am_pm: TextView = itemView.findViewById(R.id.tv_am_pm)
        val cv_am_pm_card: CardView = itemView.findViewById(R.id.cv_am_pm_card)
        val cv_time_card: CardView = itemView.findViewById(R.id.cv_time_card)
        val iv_remove: ImageView = itemView.findViewById(R.id.iv_remove)
    }
}