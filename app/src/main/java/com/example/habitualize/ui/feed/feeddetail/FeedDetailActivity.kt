package com.example.habitualize.ui.feed.feeddetail

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityFeedDetailBinding
import com.example.habitualize.ui.feed.feeddetail.adapter.CommentAdapter
import com.example.habitualize.ui.feed.feeddetail.adapter.GiverGiftAdapter
import com.example.habitualize.ui.feed.feeddetail.adapter.SendingGiftAdapter
import com.example.habitualize.ui.home.taskdetail.TaskDetailActivity
import com.example.habitualize.ui.models.DailyChallengesModel
import com.example.habitualize.ui.models.FeedCommentsModel
import com.example.habitualize.ui.models.FeedModel
import com.example.habitualize.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FeedDetailActivity : AppCompatActivity() {
    private val viewModel: FeedDetailViewModel by viewModels()
    lateinit var binding: ActivityFeedDetailBinding
    lateinit var commentAdapter: CommentAdapter
    lateinit var sendingGiftAdapter: SendingGiftAdapter
    lateinit var giverGiftAdapter: GiverGiftAdapter
    private lateinit var bottomSheetDialogForFilter: BottomSheetDialog
    private lateinit var bottomSheet: View
    var feedId = ""
    var feed = FeedModel()
    var coins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedDetailBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        feedId = intent.getStringExtra("feed_id").orEmpty()
        coins = try { SharePrefHelper.readString(userCoins)?.toInt()!! }catch (e: Exception){ 0 }

        prepareBottomSheet()
        initAdapter()
        initListeners()
        initObserver()

        viewModel.getFeedDetail(this,feedId)
    }

    private fun initObserver(){
        viewModel.feedModel.observe(this){
            feed = it
            setData(it)
        }
    }

    private fun setData(feed: FeedModel){
        Glide.with(this).load(feed.user_image).into(binding.civUserImage)
        binding.tvUserName.text = feed.user_name
        binding.tvFeedTime.text = getFormattedTimeWithDate(feed.created_at.toLong())
        binding.tvQuestion.text = feed.question
        binding.tvAnswer.text = feed.answer
        if(feed.likes.contains(SharePrefHelper.readString(userId))){
            binding.ivLikeIcon.setColorFilter(ContextCompat.getColor(this, R.color.light_theme_bold))
        }else{
            binding.ivLikeIcon.setColorFilter(ContextCompat.getColor(this, R.color.theme_light))
        }
        bottomSheet.findViewById<TextView>(R.id.tv_sending_label).text = "Send to: ${feed.user_name}"

        giverGiftAdapter.love_counter = feed.love_counter
        giverGiftAdapter.hug_counter = feed.hug_counter
        giverGiftAdapter.high_five_counter = feed.high_five_counter
        giverGiftAdapter.appreciation_counter = feed.appreciation_counter
        giverGiftAdapter.flowers_counter = feed.flowers_counter
        giverGiftAdapter.thank_you_counter = feed.thank_you_counter
        giverGiftAdapter.notifyDataSetChanged()

        commentAdapter.commentList = feed.comments_list
        commentAdapter.notifyDataSetChanged()
        binding.feedDetailCommentRv.scrollToPosition(feed.comments_list.size - 1)
    }

    private fun initAdapter() {
        commentAdapter = CommentAdapter(
            this,
            arrayListOf(),
            object : CommentAdapter.CallBacks {
                override fun onDeleteCmntEvent(model: FeedCommentsModel, position: Int) {
                    feed.comments_list.removeAt(position)
                    viewModel.deleteComment(
                        this@FeedDetailActivity,
                        feedId,
                        feed.comments_list
                    )
                }
            },
            SharePrefHelper.readInteger(selectedColorIndex)
        )
        binding.feedDetailCommentRv.adapter = commentAdapter
    }

    private fun initListeners(){
        binding.feedDetailGift.setOnClickListener {
            showBottomSheet()
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.ivLikeIcon.setOnClickListener {
            if(!feed.likes.contains(SharePrefHelper.readString(userId))){
                viewModel.hitLike(this, feed, SharePrefHelper.readString(userId).toString())
            }
        }

        binding.sendMsgIcon.setOnClickListener {
            if(binding.etComment.text.isNotEmpty()){
                viewModel.createComment(
                    this,
                    feedId,
                    FeedCommentsModel(
                        user_image = SharePrefHelper.readString(userImage).toString(),
                        user_id = SharePrefHelper.readString(userId).toString(),
                        user_name = SharePrefHelper.readString(userName).toString(),
                        created_at = "${ Calendar.getInstance().timeInMillis }",
                        comment = binding.etComment.text.trim().toString()
                    )
                )
                binding.etComment.text.clear()
                hideKeyboard(this,binding.root)
            }
        }

    }

    private fun showBottomSheet()
    {
        bottomSheetDialogForFilter.show()
    }

    private fun prepareBottomSheet(){
        bottomSheetDialogForFilter = BottomSheetDialog(this, R.style.BottomSheetDailougeTheme)
        bottomSheet = LayoutInflater.from(this)
            .inflate(
                R.layout.item_gift_bottom_sheet,
                findViewById(R.id.bottomSheet),
                true
            )
        bottomSheetDialogForFilter.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bottomSheetDialogForFilter.setContentView(bottomSheet)

        sendingGiftAdapter = SendingGiftAdapter(this, object: SendingGiftAdapter.CallBacks{
            override fun onSendGiftEvent(position: Int) {
                showConfirmationDialog(position)
            }
        })
        giverGiftAdapter = GiverGiftAdapter(this, SharePrefHelper.readInteger(selectedColorIndex))

        bottomSheet.findViewById<RecyclerView>(R.id.item_gift_bottom_sheet_rv).adapter = sendingGiftAdapter
        bottomSheet.findViewById<RecyclerView>(R.id.rv_given_gift).adapter = giverGiftAdapter
    }

    private fun showConfirmationDialog(gift_index: Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.item_send_gift_dialouge)
        dialog.show()
        var cnfrm_btn = dialog.findViewById<LinearLayout>(R.id.ll_confirm_btn)
        var gift_icon = dialog.findViewById<ImageView>(R.id.dialoge_img)
        var gif_title = dialog.findViewById<TextView>(R.id.tv_gift_title)
        when(gift_index){
            0->{
                Glide.with(this).load(R.drawable.flower_icon).into(gift_icon)
                gif_title.text = resources.getString(R.string.flower)
            }
            1->{
                Glide.with(this).load(R.drawable.hug_icon).into(gift_icon)
                gif_title.text = resources.getString(R.string.hug)
            }
            2->{
                Glide.with(this).load(R.drawable.high_five_icon).into(gift_icon)
                gif_title.text = resources.getString(R.string.high_five)
            }
            3->{
                Glide.with(this).load(R.drawable.love_icon).into(gift_icon)
                gif_title.text = resources.getString(R.string.love)
            }
            4->{
                Glide.with(this).load(R.drawable.appriciate_icon).into(gift_icon)
                gif_title.text = resources.getString(R.string.appreciation)
            }
            5->{
                Glide.with(this).load(R.drawable.thankyou_icon).into(gift_icon)
                gif_title.text =  resources.getString(R.string.thankyou)
            }
        }


        cnfrm_btn.setOnClickListener {
            if(coins >= 25) {
                coins -= 25
                SharePrefHelper.writeString(userCoins, "$coins")
                viewModel.sendGift(this, feed, gift_index)
                dialog.dismiss()
            }else{
                Toast.makeText(this, noCoinMsg, Toast.LENGTH_SHORT).show()
            }
        }
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
        binding.root.setBackgroundColor(ContextCompat.getColor(this, color))
        binding.sendMsgIcon.setColorFilter(ContextCompat.getColor(this, color))
        binding.rlCommentBg.setCardBackgroundColor(ContextCompat.getColor(this, lightColor))
    }
}