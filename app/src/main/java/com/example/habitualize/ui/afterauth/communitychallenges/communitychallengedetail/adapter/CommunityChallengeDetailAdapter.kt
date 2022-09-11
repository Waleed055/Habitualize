package com.example.habitualize.ui.afterauth.communitychallenges.communitychallengedetail.adapter

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
import com.example.habitualize.ui.afterauth.communitychallenges.adapter.CommunityChallengeAdapter

class CommunityChallengeDetailAdapter :
    RecyclerView.Adapter<CommunityChallengeDetailAdapter.InnerClassViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClassViewHolder {
        return InnerClassViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_community_challenges_detail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: InnerClassViewHolder, position: Int) {
        holder.challenge_day.text = "Day ${position + 1}"
    }

    override fun getItemCount(): Int {
        return 30
    }

    inner class InnerClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val challenge_day: TextView = itemView.findViewById(R.id.challenge_day)
    }
}