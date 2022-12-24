package com.example.habitualize.ui.feed.feeddetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitualize.R

class SendingGiftAdapter(val context: Context, val callback: CallBacks) : RecyclerView.Adapter<SendingGiftAdapter.ViewHolderSendingGiftAdapter>(){

    interface CallBacks{
        fun onSendGiftEvent(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderSendingGiftAdapter {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_gift,parent,false)
        return ViewHolderSendingGiftAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderSendingGiftAdapter, position: Int) {
        when(position){
            0->{
                Glide.with(context).load(R.drawable.flower_icon).into(holder.iv_gift_icon)
                holder.tv_gift_title.text = "Flowers"
            }
            1->{
                Glide.with(context).load(R.drawable.hug_icon).into(holder.iv_gift_icon)
                holder.tv_gift_title.text = "Hug"
            }
            2->{
                Glide.with(context).load(R.drawable.high_five_icon).into(holder.iv_gift_icon)
                holder.tv_gift_title.text = "High Five"
            }
            3->{
                Glide.with(context).load(R.drawable.love_icon).into(holder.iv_gift_icon)
                holder.tv_gift_title.text = "Love"
            }
            4->{
                Glide.with(context).load(R.drawable.appriciate_icon).into(holder.iv_gift_icon)
                holder.tv_gift_title.text = "Appreciation"
            }
            5->{
                Glide.with(context).load(R.drawable.thankyou_icon).into(holder.iv_gift_icon)
                holder.tv_gift_title.text = "Thank you"
            }
        }

        holder.itemView.setOnClickListener {
            callback.onSendGiftEvent(position)
        }
    }

    override fun getItemCount() = 6

    class ViewHolderSendingGiftAdapter(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val tv_gift_title: TextView = itemView.findViewById(R.id.tv_gift_title)
        val iv_gift_icon: ImageView = itemView.findViewById(R.id.iv_gift_icon)
    }
}