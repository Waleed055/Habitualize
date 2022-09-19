package com.example.habitualize.ui.afterauth.feed.feeddetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R

class CommentAdapter(val context: Context) :
    RecyclerView.Adapter<CommentAdapter.ViewHolderCommentAdapter>() {


    class ViewHolderCommentAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCommentAdapter {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_feed,parent,false)
        return ViewHolderCommentAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderCommentAdapter, position: Int) {}

    override fun getItemCount() = 4

}