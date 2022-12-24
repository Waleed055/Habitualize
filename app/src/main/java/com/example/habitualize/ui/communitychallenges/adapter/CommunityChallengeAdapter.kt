package com.example.habitualize.ui.communitychallenges.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.example.habitualize.ui.models.CommunityChallengeModel

class CommunityChallengeAdapter(
    val context: Context,
    var challenge_list: ArrayList<CommunityChallengeModel>,
    val callBacks: CallBacks,
    var colorPosition: Int
) : RecyclerView.Adapter<CommunityChallengeAdapter.InnerClassViewHolder>() {

    interface CallBacks {
        fun onDetailEventCall(challenge_id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClassViewHolder {
        return InnerClassViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_community_challenges, parent, false)
        )
    }

    override fun onBindViewHolder(holder: InnerClassViewHolder, position: Int) {
        var item = challenge_list[position]
        holder.like_icon.setColorFilter(getColor())
        holder.detail_icon.setBackgroundColor(getColor())

        holder.challenge_icon.text = item.challenge_emoji
        holder.challenge_title.text = item.challenge_name
        holder.like_count.text = "${item.likes.size}"

        holder.detail_icon.setOnClickListener {
            callBacks.onDetailEventCall(item.challenge_id)
        }
    }

    override fun getItemCount(): Int {
        return challenge_list.size
    }

    private fun getColor(): Int {
        return when(colorPosition){
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
    }

    inner class InnerClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val challenge_icon: TextView = itemView.findViewById(R.id.challenge_icon)
        val challenge_title: TextView = itemView.findViewById(R.id.challenge_title)
        val like_layout: LinearLayout = itemView.findViewById(R.id.like_layout)
        val like_icon: ImageView = itemView.findViewById(R.id.like_icon)
        val like_count: TextView = itemView.findViewById(R.id.like_count)
        val detail_icon: RelativeLayout = itemView.findViewById(R.id.detail_icon)
    }
}