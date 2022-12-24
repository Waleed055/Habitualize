package com.example.habitualize.ui.feed.adapter

import android.content.Context
import android.content.Intent
import android.graphics.ColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitualize.R
import com.example.habitualize.ui.feed.feeddetail.FeedDetailActivity
import com.example.habitualize.ui.models.FeedModel
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.getFormattedTimeWithDate
import com.example.habitualize.utils.userId
import de.hdodenhof.circleimageview.CircleImageView

class FeedAdapter(
    val context: Context,
    var feedList: ArrayList<FeedModel>,
    val callBacks: CallBacks
) : RecyclerView.Adapter<FeedAdapter.ViewHolderFeedAdapter>() {

    interface CallBacks{
        fun onDeleteEvent(feed: FeedModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFeedAdapter {
        return ViewHolderFeedAdapter(
            LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderFeedAdapter, position: Int) {
        var item = feedList[position]
        Glide.with(context).load(item.user_image).into(holder.civUserImage)
        holder.tvUserName.text = item.user_name
        holder.tvFeedTime.text = getFormattedTimeWithDate(item.created_at.toLong())
        holder.tvQuestion.text = item.question
        holder.tvAnswer.text = item.answer
        holder.tvCommentCounter.text = "${item.comments_list.size}"
        holder.tvLikeCounter.text = "${item.likes.size}"
        if(item.user_id == SharePrefHelper.readString(userId)){
            holder.iv_delete_btn.visibility = View.VISIBLE
        }else{
            holder.iv_delete_btn.visibility = View.GONE
        }


        holder.itemView.setOnClickListener {
            var intent = Intent(context, FeedDetailActivity::class.java)
            intent.putExtra("feed_id",item.feed_id)
            context.startActivity(intent)
        }

        holder.iv_delete_btn.setOnClickListener {
            callBacks.onDeleteEvent(item, position)
        }
    }

    override fun getItemCount() = feedList.size


    class ViewHolderFeedAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civUserImage: CircleImageView = itemView.findViewById(R.id.civ_user_image)
        val tvUserName: TextView = itemView.findViewById(R.id.tv_user_name)
        val tvFeedTime: TextView = itemView.findViewById(R.id.tv_feed_time)
        val tvQuestion: TextView = itemView.findViewById(R.id.tv_question)
        val tvAnswer: TextView = itemView.findViewById(R.id.tv_answer)
        val tvCommentCounter: TextView = itemView.findViewById(R.id.tv_comment_counter)
        val tvLikeCounter: TextView = itemView.findViewById(R.id.tv_like_counter)
        val iv_delete_btn: ImageView = itemView.findViewById(R.id.iv_delete_btn)
    }
}