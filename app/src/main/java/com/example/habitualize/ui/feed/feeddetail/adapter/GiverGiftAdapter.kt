package com.example.habitualize.ui.feed.feeddetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitualize.R

class GiverGiftAdapter(val context: Context, var colorPosition: Int) :
    RecyclerView.Adapter<GiverGiftAdapter.ViewHolderCommentAdapter>() {

    var flowers_counter: String = "0"
    var hug_counter: String = "0"
    var high_five_counter: String = "0"
    var love_counter: String = "0"
    var appreciation_counter: String = "0"
    var thank_you_counter: String = "0"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCommentAdapter {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_given_gift,parent,false)
        return ViewHolderCommentAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderCommentAdapter, position: Int) {
        holder.root.setCardBackgroundColor(getColor())
        when(position){
            0->{
                Glide.with(context).load(R.drawable.flower_icon).into(holder.iv_gift_icon)
                holder.tv_gift_counter.text = "$flowers_counter Flower"
            }
            1->{
                Glide.with(context).load(R.drawable.hug_icon).into(holder.iv_gift_icon)
                holder.tv_gift_counter.text = "$hug_counter Hug"
            }
            2->{
                Glide.with(context).load(R.drawable.high_five_icon).into(holder.iv_gift_icon)
                holder.tv_gift_counter.text = "$high_five_counter High Five"
            }
            3->{
                Glide.with(context).load(R.drawable.love_icon).into(holder.iv_gift_icon)
                holder.tv_gift_counter.text = "$love_counter Love"
            }
            4->{
                Glide.with(context).load(R.drawable.appriciate_icon).into(holder.iv_gift_icon)
                holder.tv_gift_counter.text = "$appreciation_counter Appreciation"
            }
            5->{
                Glide.with(context).load(R.drawable.thankyou_icon).into(holder.iv_gift_icon)
                holder.tv_gift_counter.text = "$thank_you_counter Thank you"
            }
        }

    }

    override fun getItemCount() = 6

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

    class ViewHolderCommentAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_gift_counter: TextView = itemView.findViewById(R.id.tv_gift_counter)
        val iv_gift_icon: ImageView = itemView.findViewById(R.id.iv_gift_icon)
        val root: CardView = itemView.findViewById(R.id.root)
    }

}