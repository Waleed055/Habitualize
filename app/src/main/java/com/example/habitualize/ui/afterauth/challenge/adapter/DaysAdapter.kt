package com.example.habitualize.ui.afterauth.challenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
class DaysAdapter(val context: Context) : RecyclerView.Adapter<DaysAdapter.ViewHolderDaysAdapter>() {


    class ViewHolderDaysAdapter(itemView : View) : RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDaysAdapter {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_day,parent,false)
        return ViewHolderDaysAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDaysAdapter, position: Int) {
    }

    override fun getItemCount() = 10

}