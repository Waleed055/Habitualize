package com.example.habitualize.ui.afterauth.feed.feeddetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityFeedDetailBinding
import com.example.habitualize.ui.afterauth.feed.feeddetail.adapter.CommentAdapter
import com.example.habitualize.ui.afterauth.feed.feeddetail.adapter.SendingGiftAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class FeedDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityFeedDetailBinding
    lateinit var commentAdapter: CommentAdapter
    lateinit var sendingGiftAdapter: SendingGiftAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

    }

    private fun initAdapter() {
        commentAdapter = CommentAdapter(this)
        binding.feedDetailCommentRv.adapter = commentAdapter

        sendingGiftAdapter = SendingGiftAdapter(this)
    }

    private fun showBottomSheet()
    {
        val bottomSheetDialogForFilter = BottomSheetDialog(this, R.style.BottomSheetDailougeTheme)
        val bottomSheet = LayoutInflater.from(this)
            .inflate(
                R.layout.item_gift_bottom_sheet,
                findViewById(R.id.bottomSheet),
                true
            )
        bottomSheetDialogForFilter.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bottomSheetDialogForFilter.setContentView(bottomSheet)

        bottomSheet.findViewById<RecyclerView>(R.id.item_gift_bottom_sheet_rv).adapter = sendingGiftAdapter
        
    }


}