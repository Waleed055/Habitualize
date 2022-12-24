package com.example.habitualize.ui.hidentask

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityHiddenTaskBinding
import com.example.habitualize.databinding.FragmentTaskBinding
import com.example.habitualize.ui.hidentask.adapter.HiddenTaskAdapter
import com.example.habitualize.ui.hidentask.hiddentaskdetail.HiddenTaskDetailActivity
import com.example.habitualize.ui.home.Adapter.TaskAdapter
import com.example.habitualize.ui.home.HomeActivity
import com.example.habitualize.ui.home.taskdetail.TaskDetailActivity
import com.example.habitualize.ui.models.DailyChallengesModel
import com.example.habitualize.ui.premium.PremiumActivity
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.changeStatusBarColor
import com.example.habitualize.utils.openTaskCounter
import com.example.habitualize.utils.selectedColorIndex
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HiddenTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHiddenTaskBinding
    private val viewModel: HiddenTaskViewModel by viewModels()
    lateinit var adapter: HiddenTaskAdapter
    var button: CardView? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiddenTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        initListeners()
        initAdapter()
        initObservers()
        viewModel.getHiddenChallenges(this, Locale.getDefault().language)
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener { 
            finish()
        }
    }

    private fun initAdapter() {
        adapter =
            HiddenTaskAdapter(this, arrayListOf(), object : HiddenTaskAdapter.TaskItemListener {
                override fun onItemClicked(position: Int, dailyChallengesModel: DailyChallengesModel) {
                    if(dailyChallengesModel.isOpened){
                        var intent = Intent(this@HiddenTaskActivity, HiddenTaskDetailActivity::class.java)
                        intent.putExtra("challenge_name", dailyChallengesModel.challenge_name)
                        startActivity(intent)
                    }else{
                        showConfirmationDialog(dailyChallengesModel)
                    }
                }
            },SharePrefHelper.readInteger(selectedColorIndex))
        binding.taskRv.adapter = adapter
    }

    private fun initObservers() {
        viewModel.challengesList.observe(this) {
            if(it.isEmpty()){
                binding.dataLayout.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
                return@observe
            }else{
                binding.dataLayout.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
            }
            adapter.challengesList.clear()
            adapter.challengesList.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }


    private fun showConfirmationDialog(dailyChallengesModel: DailyChallengesModel) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.item_confirmation_dialouge)
        button = dialog.findViewById(R.id.confirmation_btn)
        var tv_challenge_emoji = dialog.findViewById<TextView>(R.id.tv_challenge_emoji)
        var icon = dialog.findViewById<ImageView>(R.id.dialoge_img)
        var tv_challenge_name = dialog.findViewById<TextView>(R.id.tv_challenge_name)
        Glide.with(this).load(Uri.parse(dailyChallengesModel.challenge_emoji)).into(icon)
        tv_challenge_name.text = dailyChallengesModel.challenge_name
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        dialog.show()

        if(dailyChallengesModel.isUserLocal){
            tv_challenge_emoji.visibility = View.VISIBLE
            icon.visibility = View.GONE
            tv_challenge_emoji.text = dailyChallengesModel.challenge_emoji
        }else{
            tv_challenge_emoji.visibility = View.GONE
            icon.visibility = View.VISIBLE
        }
        button?.setOnClickListener {
            dialog.dismiss()
            var intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra("challenge_name", dailyChallengesModel.challenge_name)
            intent.putExtra("isHidden", true)
            startActivity(intent)

            //counter
            var _counter  = SharePrefHelper.readInteger(openTaskCounter) + 1
            SharePrefHelper.writeInteger(openTaskCounter, _counter)
        }
    }

    private fun updateTheme(position: Int){
        when(position){
            0->{ setThemeColor(R.color.theme) }
            1->{ setThemeColor(R.color.theme_2) }
            2->{ setThemeColor(R.color.theme_3) }
            3->{ setThemeColor(R.color.theme_4) }
            4->{ setThemeColor(R.color.theme_5) }
            5->{ setThemeColor(R.color.theme_6) }
            6->{ setThemeColor(R.color.theme_7) }
            7->{ setThemeColor(R.color.theme_8) }
        }
    }

    private fun setThemeColor(color: Int){
        changeStatusBarColor(this, color)
        binding.parent.setBackgroundColor(ContextCompat.getColor(this, color))
        button.let {  it?.setCardBackgroundColor(ContextCompat.getColor(this, color)) }
    }
    
}