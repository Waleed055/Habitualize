package com.example.habitualize.ui.introscreens.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R

class ViewPagerAdapter(val context: Context,private val listOfImages : List<Int>) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerAdapterHolder>() {

    class ViewPagerAdapterHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        var imageView = itemView.findViewById<ImageView>(R.id.item_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapterHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_intro_gif,parent,false)
        return ViewPagerAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerAdapterHolder, position: Int) {
        holder.imageView.setImageResource(listOfImages[position])
    }

    override fun getItemCount(): Int {
        return listOfImages.size
    }

}