package com.example.habitualize.ui.home.taskdetail.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.example.habitualize.ui.models.ChallengeDetailModel
import com.example.habitualize.utils.changeStatusBarColor

class TaskDetailAdapter(
    val context: Context,
    var tasksList: ArrayList<ChallengeDetailModel>,
    val callBacks: CallBacks,
    var colorPosition: Int
) : RecyclerView.Adapter<TaskDetailAdapter.ViewHolderDaysAdapter>() {

    interface CallBacks {
        fun onClickTask(model: ChallengeDetailModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDaysAdapter {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent, false)
        return ViewHolderDaysAdapter(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDaysAdapter, position: Int) {
        var item = tasksList[position]
        holder.challenge_text.text = "${position + 1}"

        holder.itemView.setOnClickListener {
            callBacks.onClickTask(item, position)
        }

        if (item.isOpened) {
            holder.challenge_card.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            holder.challenge_text.setTextColor(ContextCompat.getColor(context, R.color.black))
        } else {
            holder.challenge_text.setTextColor(ContextCompat.getColor(context, R.color.white))
           holder.challenge_card.setCardBackgroundColor(getColor())
        }

    }

    override fun getItemCount() = tasksList.size

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

    class ViewHolderDaysAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val challenge_card: CardView = itemView.findViewById(R.id.challenge_card)
        val challenge_text: TextView = itemView.findViewById(R.id.challenge_text)
    }

}