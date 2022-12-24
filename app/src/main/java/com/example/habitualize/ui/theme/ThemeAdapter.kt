package com.example.habitualize.ui.theme

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.example.habitualize.db.entities.DBThemeModel
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.selectedColorIndex
import com.google.android.material.card.MaterialCardView

class ThemeAdapter(
    val context: Context,
    val callBacks: CallBacks,
    var selectedTheme: Int,
    var purchasedList: ArrayList<Int>
) : RecyclerView.Adapter<ThemeAdapter.MyViewHolder>() {

    interface CallBacks {
        fun onSelectColorEventCall(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_themes_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(purchasedList.contains(position) || position == 0){
            holder.ll_price.visibility = View.GONE
        }else{
            holder.ll_price.visibility = View.VISIBLE
        }

        if (selectedTheme == position){
            holder.cv_color_card.strokeWidth = 5
            holder.cv_color_card.strokeColor = ContextCompat.getColor(context, R.color.warning_red)
        }else{
            holder.cv_color_card.strokeWidth = 0
        }

        holder.cv_color_card.radius = 10F
        when(position){
            0->{ holder.cv_color_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme)) }
            1->{ holder.cv_color_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme_2)) }
            2->{ holder.cv_color_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme_3)) }
            3->{ holder.cv_color_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme_4)) }
            4->{ holder.cv_color_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme_5)) }
            5->{ holder.cv_color_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme_6)) }
            6->{ holder.cv_color_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme_7)) }
            7->{ holder.cv_color_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme_8)) }
        }


        holder.itemView.setOnClickListener {
            callBacks.onSelectColorEventCall(position)
        }

    }

    override fun getItemCount() = 8

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cv_color_card: MaterialCardView = itemView.findViewById(R.id.cv_color_card)
        val ll_price: LinearLayout = itemView.findViewById(R.id.ll_price)
    }
}