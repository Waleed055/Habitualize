package com.example.habitualize.ui.afterauth.feed.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.example.habitualize.ui.afterauth.challenge.adapter.DaysAdapter

class FeedAdapter(val context: Context) :
    RecyclerView.Adapter<FeedAdapter.ViewHolderFeedAdapter>() {


    class ViewHolderFeedAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFeedAdapter {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_feed,parent,false)
        return ViewHolderFeedAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderFeedAdapter, position: Int) {
    }

    override fun getItemCount() = 4

}