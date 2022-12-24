package com.example.habitualize.ui.feed.feeddetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitualize.R
import com.example.habitualize.ui.models.FeedCommentsModel
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.getFormattedTime
import com.example.habitualize.utils.userId
import de.hdodenhof.circleimageview.CircleImageView

class CommentAdapter(
    val context: Context,
    var commentList: ArrayList<FeedCommentsModel>,
    var callbacks: CallBacks,
    var colorPosition: Int
) : RecyclerView.Adapter<CommentAdapter.ViewHolderCommentAdapter>() {

    interface CallBacks{
        fun onDeleteCmntEvent(model: FeedCommentsModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCommentAdapter {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return ViewHolderCommentAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderCommentAdapter, position: Int) {
        var item = commentList[position]
        Glide.with(context).load(item.user_image).into(holder.civ_user_image)
        holder.tv_user_name.text = item.user_name
        holder.tv_comment_time.text = getFormattedTime(item.created_at.toLong())
        holder.tv_comment.text = item.comment
        holder.iv_delete_cmnt.setColorFilter(getColor())
        if(item.user_id == SharePrefHelper.readString(userId)){
            holder.iv_delete_cmnt.visibility = View.VISIBLE
        }else{
            holder.iv_delete_cmnt.visibility = View.GONE
        }
        holder.iv_delete_cmnt.setOnClickListener {
            callbacks.onDeleteCmntEvent(item, position)
        }
    }

    override fun getItemCount() = commentList.size

    private fun getColor(): Int {
        return when(colorPosition){
            0->{
                ContextCompat.getColor(context, R.color.theme)
            }
            1->{
                ContextCompat.getColor(context, R.color.theme_2)
            }
            2->{
                ContextCompat.getColor(context, R.color.theme_3)
            }
            3->{
                ContextCompat.getColor(context, R.color.theme_4)
            }
            4->{
                ContextCompat.getColor(context, R.color.theme_5)
            }
            5->{
                ContextCompat.getColor(context, R.color.theme_6)
            }
            6->{
                ContextCompat.getColor(context, R.color.theme_7)
            }
            7->{
                ContextCompat.getColor(context, R.color.theme_8)
            }
            else->{
                ContextCompat.getColor(context, R.color.theme)
            }
        }
    }

    class ViewHolderCommentAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civ_user_image: CircleImageView = itemView.findViewById(R.id.civ_user_image)
        val tv_user_name: TextView = itemView.findViewById(R.id.tv_user_name)
        val tv_comment_time: TextView = itemView.findViewById(R.id.tv_comment_time)
        val tv_comment: TextView = itemView.findViewById(R.id.tv_comment)
        val iv_delete_cmnt: ImageView = itemView.findViewById(R.id.iv_delete_cmnt)
    }
}