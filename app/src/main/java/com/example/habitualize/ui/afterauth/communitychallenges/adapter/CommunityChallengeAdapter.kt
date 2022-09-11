package com.example.habitualize.ui.afterauth.communitychallenges.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R

class CommunityChallengeAdapter(val context: Context, val callBacks: CallBacks) :
    RecyclerView.Adapter<CommunityChallengeAdapter.InnerClassViewHolder>() {

    interface CallBacks {
        fun onDetailEventCall()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClassViewHolder {
        return InnerClassViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_community_challenges, parent, false)
        )
    }

    override fun onBindViewHolder(holder: InnerClassViewHolder, position: Int) {
        holder.detail_icon.setOnClickListener {
            callBacks.onDetailEventCall()
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class InnerClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val challenge_icon : TextView = itemView.findViewById(R.id.challenge_icon)
        val challenge_title : TextView = itemView.findViewById(R.id.challenge_title)
        val like_layout : LinearLayout = itemView.findViewById(R.id.like_layout)
        val like_icon : ImageView = itemView.findViewById(R.id.like_icon)
        val like_count : TextView = itemView.findViewById(R.id.like_count)
        val detail_icon : RelativeLayout = itemView.findViewById(R.id.detail_icon)
    }
}