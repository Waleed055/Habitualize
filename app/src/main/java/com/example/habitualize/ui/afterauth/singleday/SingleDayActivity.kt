package com.example.habitualize.ui.afterauth.singleday

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivitySingleBinding
import com.example.habitualize.ui.afterauth.taskschedule.TaskSchedule
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.droidsonroids.gif.GifImageView
import tyrantgit.explosionfield.ExplosionField


class SingleDayActivity : AppCompatActivity() {

    lateinit var binding: ActivitySingleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()

    }

    private fun initListener(){
        binding.breakView.setOnClickListener {
            var explosionField = ExplosionField.attach2Window(this)
            explosionField.explode(binding.breakView)
            binding.breakView.visibility = View.GONE
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.completeTaskButton.setOnClickListener {
            if(binding.breakView.visibility == View.GONE){
                showTaskCompleteDialog()
            }
        }

        binding.shareCard.setOnClickListener {
            if (binding.breakView.visibility == View.GONE) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, binding.challengeText.text)
                startActivity(Intent.createChooser(intent, "choose one"))
            }
        }
    }

    private fun showTaskCompleteDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.item_task_complete_dialouge)
        dialog.show()
        var button = dialog.findViewById<TextView>(R.id.done_btn)



        button.setOnClickListener {
            dialog.dismiss()
        }
    }

}