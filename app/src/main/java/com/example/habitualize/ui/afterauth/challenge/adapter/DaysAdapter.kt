package com.example.habitualize.ui.afterauth.challenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.google.android.material.textfield.TextInputEditText

class DaysAdapter(val context: Context) : RecyclerView.Adapter<DaysAdapter.ViewHolderDaysAdapter>() {


    class ViewHolderDaysAdapter(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val challenge_text_input_edit_text : TextInputEditText = itemView.findViewById(R.id.challenge_text_input_edit_text)
        val save_button : ImageView = itemView.findViewById(R.id.save_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDaysAdapter {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_day,parent,false)
        return ViewHolderDaysAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDaysAdapter, position: Int) {
        holder.challenge_text_input_edit_text.setOnClickListener {
            holder.save_button.visibility = View.VISIBLE
        }
    }

    override fun getItemCount() = 10

}