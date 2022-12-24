package com.example.habitualize.ui.challenge.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.lang.Exception

class DaysAdapter(
    val context: Context,
    val callBacks: CallBacks,
    var colorPosition: Int
) : RecyclerView.Adapter<DaysAdapter.ViewHolderDaysAdapter>() {

    var challenges = arrayListOf<String>()

    interface CallBacks {
        fun onSaveTaskEvent(position: Int, task: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDaysAdapter {
        return ViewHolderDaysAdapter(
            LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderDaysAdapter, position: Int) {
        holder.outlinedTextField.boxStrokeColor = ContextCompat.getColor(context, R.color.hint_color)
        holder.outlinedTextField.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.hint_color))
        holder.save_button.setColorFilter(getMyColor())
        holder.outlinedTextField.hint = "${context.resources.getString(R.string.day)} ${position+1}"
        try {
            var item = challenges[position]
            holder.challenge_text_input_edit_text.setText(item)
        }catch (e: Exception){
            Log.e("ERROR","error in fetching list index")
        }
        holder.challenge_text_input_edit_text.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                holder.save_button.visibility = View.VISIBLE
            }
        }
        holder.save_button.setOnClickListener {
            holder.save_button.setColorFilter(ContextCompat.getColor(context, R.color.theme_light))
            holder.challenge_text_input_edit_text.isFocusable = false
            holder.challenge_text_input_edit_text.isClickable = false
            holder.outlinedTextField.boxStrokeColor = ContextCompat.getColor(context, R.color.theme_light)
            callBacks.onSaveTaskEvent(position,holder.challenge_text_input_edit_text.text.toString())
        }
    }

    override fun getItemCount() = 30

    private fun getMyColor(): Int {
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
        val challenge_text_input_edit_text: TextInputEditText =
            itemView.findViewById(R.id.challenge_text_input_edit_text)
        val outlinedTextField: TextInputLayout = itemView.findViewById(R.id.outlinedTextField)
        val save_button: ImageView = itemView.findViewById(R.id.save_button)
    }
}