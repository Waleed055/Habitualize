package com.example.habitualize.ui.home.taskdetail.singleday

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivitySingleBinding
import com.example.habitualize.managers.AdsManager
import com.example.habitualize.ui.models.ChallengeDetailModel
import com.example.habitualize.utils.*
import dagger.hilt.android.AndroidEntryPoint
import tyrantgit.explosionfield.ExplosionField
import java.util.*

@AndroidEntryPoint
class SingleDayActivity : AppCompatActivity() {
    private val viewModel: SingleDayViewModel by viewModels()

    lateinit var binding: ActivitySingleBinding
    lateinit var task: ChallengeDetailModel
    var position = 0
    var coins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleBinding.inflate(layoutInflater)
        task = intent.getSerializableExtra("task") as ChallengeDetailModel
        position = intent.getIntExtra("position",0)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        coins = try { SharePrefHelper.readString(userCoins)?.toInt()!! }catch (e: Exception){ 0 }


        initListener()
        setData()
    }

    private fun setData(){
        binding.tvTaskDay.text = "${resources.getString(R.string.day )} ${position + 1}"
        binding.challengeText.text = task.challenge
        binding.tvCoinCounter.text = SharePrefHelper.readString(userCoins)
    }

    private fun initListener() {

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.breakView.setOnClickListener {
            completeTask()
        }

        binding.completeTaskButton.setOnClickListener {
            completeTask()
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


    private fun completeTask(){
            coins += 10
            binding.tvCoinCounter.text = "$coins"
            SharePrefHelper.writeString(userCoins,"$coins")
            // ------------------------
            var explosionField = ExplosionField.attach2Window(this)
            explosionField.explode(binding.breakView)
            binding.breakView.visibility = View.GONE
            task.isOpened = true
            updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
            //---------------------------
            viewModel.updateTaskOpenDate(
                task.id,
                Calendar.getInstance().timeInMillis,
                task.challenge_name,
                SharePrefHelper.readString(languageCode).toString()
            )
            viewModel.openChallenge(task.challenge_name, SharePrefHelper.readString(languageCode).toString())
            Handler(Looper.myLooper()!!).postDelayed({
                showTaskCompleteDialog()
            },1000)

            //----------------------------------------------------------------------
            //counter
            var _counter  = SharePrefHelper.readInteger(completeTaskCounter) + 1
            SharePrefHelper.writeInteger(completeTaskCounter, _counter)
    }

    private fun showTaskCompleteDialog() {
        try {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.item_task_complete_dialouge)
            dialog.show()
            var button = dialog.findViewById<TextView>(R.id.done_btn)

            button.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setOnDismissListener { AdsManager.getInstance().showInterstitial(this) }

        }catch (e:Exception){}
    }

    private fun updateTheme(position: Int){
        when(position){
            0->{ setThemeColor(R.color.theme, R.color.theme_light) }
            1->{ setThemeColor(R.color.theme_2, R.color.theme_2_light) }
            2->{ setThemeColor(R.color.theme_3, R.color.theme_3_light) }
            3->{ setThemeColor(R.color.theme_4, R.color.theme_4_light) }
            4->{ setThemeColor(R.color.theme_5, R.color.theme_5_light) }
            5->{ setThemeColor(R.color.theme_6, R.color.theme_6_light) }
            6->{ setThemeColor(R.color.theme_7, R.color.theme_7_light) }
            7->{ setThemeColor(R.color.theme_8, R.color.theme_8_light) }
        }
    }

    private fun setThemeColor(color: Int, lightColor: Int){
        changeStatusBarColor(this, color)
        binding.cvPremiumCard.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.shareCard.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.parentLayout.setBackgroundColor(ContextCompat.getColor(this, color))
        binding.breakView.setBackgroundColor(ContextCompat.getColor(this, lightColor))

        if(task.isOpened){
            binding.breakView.visibility = View.GONE
            binding.completeTaskButton.setCardBackgroundColor(ContextCompat.getColor(this, lightColor))
            binding.completeTaskButton.isClickable = false
        }else{
            binding.breakView.visibility = View.VISIBLE
            binding.completeTaskButton.setCardBackgroundColor(ContextCompat.getColor(this, color))
            binding.completeTaskButton.isClickable = true
        }
    }
}










