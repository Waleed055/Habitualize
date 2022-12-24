package com.example.habitualize.ui.apptasks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.example.habitualize.ui.models.AppTaskModel
import com.example.habitualize.utils.*
import me.tankery.lib.circularseekbar.CircularSeekBar

class AppTasksAdapter(
    val context: Context,
    val callBack: CallBacks,
    var colorPosition: Int
    ): RecyclerView.Adapter<AppTasksAdapter.ViewHolderDaysAdapter>() {
    var taskList = ArrayList<AppTaskModel>()

    interface CallBacks{
        fun onGetRewardEvent(task_id: Long, reward: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDaysAdapter {
        var view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gratitude_reward, parent, false)
        return ViewHolderDaysAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDaysAdapter, position: Int) {
        var item = taskList[position]
        holder.tv_title.text = item.reward_text
        holder.cb_completed_tasks.pointerColor = getColor(false)
        holder.cb_completed_tasks.circleColor = getColor(true)
        holder.tv_total_completed_tasks.setTextColor(getColor(true))
        holder.cb_completed_tasks.circleProgressColor = getColor(false)
        if (item.isCompleted){
            holder.rl_earn_reward.setCardBackgroundColor(getColor(true))
        }else{
            holder.rl_earn_reward.setCardBackgroundColor(getColor(false))
        }

        holder.rl_earn_reward.setOnClickListener {
            if(!item.isCompleted){
                getReward(item)
            }
        }

        when (item.type) {
            openAppCounter -> {
                var _counter = SharePrefHelper.readInteger(openAppCounter)
                holder.tv_total_completed_tasks.text = "$_counter/${item.target}"
                holder.cb_completed_tasks.progress = ((_counter.toFloat() / item.target.toFloat()) * 100)

                if(_counter >= item.target){
                    holder.rl_earn_reward.visibility = View.VISIBLE
                    holder.rl_progress.visibility = View.GONE
                }else{
                    holder.rl_earn_reward.visibility = View.GONE
                    holder.rl_progress.visibility = View.VISIBLE
                }
            }
            openTaskCounter -> {
                var _counter = SharePrefHelper.readInteger(openTaskCounter)
                holder.tv_total_completed_tasks.text = "$_counter/${item.target}"
                holder.cb_completed_tasks.progress = ((_counter.toFloat() / item.target.toFloat()) * 100)
                if(_counter >= item.target){
                    holder.rl_earn_reward.visibility = View.VISIBLE
                    holder.rl_progress.visibility = View.GONE
                }else{
                    holder.rl_earn_reward.visibility = View.GONE
                    holder.rl_progress.visibility = View.VISIBLE
                }
            }
            completeTaskCounter -> {
                var _counter = SharePrefHelper.readInteger(completeTaskCounter)
                holder.tv_total_completed_tasks.text = "$_counter/${item.target}"
                holder.cb_completed_tasks.progress = ((_counter.toFloat() / item.target.toFloat()) * 100)
                if(_counter >= item.target){
                    holder.rl_earn_reward.visibility = View.VISIBLE
                    holder.rl_progress.visibility = View.GONE
                }else{
                    holder.rl_earn_reward.visibility = View.GONE
                    holder.rl_progress.visibility = View.VISIBLE
                }
            }
            addTaskCounter -> {
                var _counter = SharePrefHelper.readInteger(addTaskCounter)
                holder.tv_total_completed_tasks.text = "$_counter/${item.target}"
                holder.cb_completed_tasks.progress = ((_counter.toFloat() / item.target.toFloat()) * 100)
                if(_counter >= item.target){
                    holder.rl_earn_reward.visibility = View.VISIBLE
                    holder.rl_progress.visibility = View.GONE
                }else{
                    holder.rl_earn_reward.visibility = View.GONE
                    holder.rl_progress.visibility = View.VISIBLE
                }
            }
            addCommunityChallengeCounter -> {
                var _counter = SharePrefHelper.readInteger(addCommunityChallengeCounter)
                holder.tv_total_completed_tasks.text = "$_counter/${item.target}"
                holder.cb_completed_tasks.progress = ((_counter.toFloat() / item.target.toFloat()) * 100)
                if(_counter >= item.target){
                    holder.rl_earn_reward.visibility = View.VISIBLE
                    holder.rl_progress.visibility = View.GONE
                }else{
                    holder.rl_earn_reward.visibility = View.GONE
                    holder.rl_progress.visibility = View.VISIBLE
                }
            }
            shareAppCounter -> {
                var _counter = SharePrefHelper.readInteger(shareAppCounter)
                holder.tv_total_completed_tasks.text = "$_counter/${item.target}"
                holder.cb_completed_tasks.progress = ((_counter.toFloat() / item.target.toFloat()) * 100)
                if(_counter >= item.target){
                    holder.rl_earn_reward.visibility = View.VISIBLE
                    holder.rl_progress.visibility = View.GONE
                }else{
                    holder.rl_earn_reward.visibility = View.GONE
                    holder.rl_progress.visibility = View.VISIBLE
                }
            }
            purchaseThemeCounter -> {
                var _counter = SharePrefHelper.readInteger(purchaseThemeCounter)
                holder.tv_total_completed_tasks.text = "$_counter/${item.target}"
                holder.cb_completed_tasks.progress = ((_counter.toFloat() / item.target.toFloat()) * 100)
                if(_counter >= item.target){
                    holder.rl_earn_reward.visibility = View.VISIBLE
                    holder.rl_progress.visibility = View.GONE
                }else{
                    holder.rl_earn_reward.visibility = View.GONE
                    holder.rl_progress.visibility = View.VISIBLE
                }
            }
            gratitudeScratchCounter -> {
                var _counter = SharePrefHelper.readInteger(gratitudeScratchCounter)
                holder.tv_total_completed_tasks.text = "$_counter/${item.target}"
                holder.cb_completed_tasks.progress = ((_counter.toFloat() / item.target.toFloat()) * 100)
                if(_counter >= item.target){
                    holder.rl_earn_reward.visibility = View.VISIBLE
                    holder.rl_progress.visibility = View.GONE
                }else{
                    holder.rl_earn_reward.visibility = View.GONE
                    holder.rl_progress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getReward(appTaskModel: AppTaskModel){
        when (appTaskModel.type) {
            openAppCounter -> {
                var _counter = SharePrefHelper.readInteger(openAppCounter)
                if(_counter >= appTaskModel.target){
                    callBack.onGetRewardEvent(appTaskModel.id,appTaskModel.reward)
                }
            }
            openTaskCounter -> {
                var _counter = SharePrefHelper.readInteger(openTaskCounter)
                if(_counter >= appTaskModel.target){
                    callBack.onGetRewardEvent(appTaskModel.id,appTaskModel.reward)
                }
            }
            completeTaskCounter -> {
                var _counter = SharePrefHelper.readInteger(completeTaskCounter)
                if(_counter >= appTaskModel.target){
                    callBack.onGetRewardEvent(appTaskModel.id,appTaskModel.reward)
                }
            }
            addTaskCounter -> {
                var _counter = SharePrefHelper.readInteger(addTaskCounter)
                if(_counter >= appTaskModel.target){
                    callBack.onGetRewardEvent(appTaskModel.id,appTaskModel.reward)
                }
            }
            addCommunityChallengeCounter -> {
                var _counter = SharePrefHelper.readInteger(addCommunityChallengeCounter)
                if(_counter >= appTaskModel.target){
                    callBack.onGetRewardEvent(appTaskModel.id,appTaskModel.reward)
                }
            }
            shareAppCounter -> {
                var _counter = SharePrefHelper.readInteger(shareAppCounter)
                if(_counter >= appTaskModel.target){
                    callBack.onGetRewardEvent(appTaskModel.id,appTaskModel.reward)
                }
            }
            purchaseThemeCounter -> {
                var _counter = SharePrefHelper.readInteger(purchaseThemeCounter)
                if(_counter >= appTaskModel.target){
                    callBack.onGetRewardEvent(appTaskModel.id,appTaskModel.reward)
                }
            }
            gratitudeScratchCounter -> {
                var _counter = SharePrefHelper.readInteger(gratitudeScratchCounter)
                if(_counter >= appTaskModel.target){
                    callBack.onGetRewardEvent(appTaskModel.id,appTaskModel.reward)
                }
            }
        }
    }

    override fun getItemCount() = taskList.size

    private fun getColor(isLight: Boolean): Int {
        return if(!isLight){
            when(colorPosition){
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
        }else {
            when(colorPosition){
                0->{
                    ContextCompat.getColor(context, R.color.theme_light)
                }
                1->{
                    ContextCompat.getColor(context, R.color.theme_2_light)
                }
                2->{
                    ContextCompat.getColor(context, R.color.theme_3_light)
                }
                3->{
                    ContextCompat.getColor(context, R.color.theme_4_light)
                }
                4->{
                    ContextCompat.getColor(context, R.color.theme_5_light)
                }
                5->{
                    ContextCompat.getColor(context, R.color.theme_6_light)
                }
                6->{
                    ContextCompat.getColor(context, R.color.theme_7_light)
                }
                7->{
                    ContextCompat.getColor(context, R.color.theme_8_light)
                }
                else->{
                    ContextCompat.getColor(context, R.color.theme_light)
                }
            }
        }
    }

    class ViewHolderDaysAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_title: TextView = itemView.findViewById(R.id.tv_title)
        val tv_total_completed_tasks: TextView =
            itemView.findViewById(R.id.tv_total_completed_tasks)
        val cb_completed_tasks: CircularSeekBar = itemView.findViewById(R.id.cb_completed_tasks)
        val rl_earn_reward: CardView = itemView.findViewById(R.id.rl_earn_reward)
        val rl_progress: RelativeLayout = itemView.findViewById(R.id.rl_progress)
    }

}