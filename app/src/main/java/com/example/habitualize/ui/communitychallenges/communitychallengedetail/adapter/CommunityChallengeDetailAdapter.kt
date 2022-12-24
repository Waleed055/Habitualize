package com.example.habitualize.ui.communitychallenges.communitychallengedetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R

class CommunityChallengeDetailAdapter(
    var context: Context,
    var challengeList: ArrayList<String>,
    var colorPosition: Int
): RecyclerView.Adapter<CommunityChallengeDetailAdapter.InnerClassViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClassViewHolder {
        return InnerClassViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_community_challenges_detail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: InnerClassViewHolder, position: Int) {
        holder.challenge_day.text = "Day ${position + 1}"
        holder.challenge_text.text = challengeList[position]
        holder.cv_root.setCardBackgroundColor(getColor())
    }

    override fun getItemCount(): Int {
        return challengeList.size
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
    inner class InnerClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val challenge_day: TextView = itemView.findViewById(R.id.challenge_day)
        val challenge_text: TextView = itemView.findViewById(R.id.challenge_text)
        val cv_root: CardView = itemView.findViewById(R.id.cv_root)
    }
}