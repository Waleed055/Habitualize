package com.example.habitualize.ui.home.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitualize.R
import com.example.habitualize.ui.models.DailyChallengesModel

class TaskAdapter(
    val context: Context,
    var challengesList: ArrayList<DailyChallengesModel>,
    val taskItemListener: TaskItemListener,
    var colorPosition: Int
) : RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder>() {

    interface TaskItemListener {
        fun onItemClicked(position: Int, dailyChallengesModel: DailyChallengesModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {
        var item = challengesList[position]
        holder.root.setCardBackgroundColor(getColor())
        holder.root.clipToOutline = false;
        if(item.isUserLocal){
            holder.tv_challenge_emoji.visibility = View.VISIBLE
            holder.iv_task_icon.visibility = View.GONE
            holder.tv_challenge_emoji.text = item.challenge_emoji
        }else{
            holder.tv_challenge_emoji.visibility = View.GONE
            holder.iv_task_icon.visibility = View.VISIBLE
            Glide.with(context)
                .load(Uri.parse(item.challenge_emoji))
                .into(holder.iv_task_icon)
        }

        holder.tv_task_name.text = item.challenge_name
        holder.itemView.setOnClickListener {
            taskItemListener.onItemClicked(position, item)
        }

    }

    override fun getItemCount() = challengesList.size

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

    class TaskAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_task_icon: ImageView = itemView.findViewById(R.id.iv_task_icon)
        val tv_task_name: TextView = itemView.findViewById(R.id.tv_task_name)
        val tv_challenge_emoji: TextView = itemView.findViewById(R.id.tv_challenge_emoji)
        val root: CardView = itemView.findViewById(R.id.root)


        //
    }
}