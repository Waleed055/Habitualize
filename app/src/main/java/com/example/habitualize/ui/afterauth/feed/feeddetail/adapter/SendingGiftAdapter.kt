package com.example.habitualize.ui.afterauth.feed.feeddetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R

class SendingGiftAdapter(val context: Context) : RecyclerView.Adapter<SendingGiftAdapter.ViewHolderSendingGiftAdapter>(){

    class ViewHolderSendingGiftAdapter(itemView : View) : RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderSendingGiftAdapter {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_feed,parent,false)
        return ViewHolderSendingGiftAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderSendingGiftAdapter, position: Int) {}

    override fun getItemCount() = 4

}