package com.example.habitualize.ui.afterauth.challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityChallengeBinding
import com.example.habitualize.databinding.ActivityNotificationBinding
import com.example.habitualize.ui.afterauth.challenge.adapter.DaysAdapter
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.EmojiPopup
import com.vanniktech.emoji.google.GoogleEmojiProvider

class ChallengeActivity : AppCompatActivity() {

    lateinit var binding: ActivityChallengeBinding
    lateinit var daysAdapter: DaysAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EmojiManager.install(GoogleEmojiProvider())
        binding = ActivityChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initListener()

    }

    private fun initListener() {
        val emojiPopup = EmojiPopup(
            binding.rootView, binding.fragmentProfileFeelingEmoji,
            onSoftKeyboardCloseListener = {
                if (binding.fragmentProfileFeelingEmoji.text.toString().isEmpty()) {
                    binding.addEmojiIcon.visibility = View.VISIBLE
                }
            },
            onEmojiClickListener = {
                println("===EMOJI==== ${it.unicode}")
                binding.addEmojiIcon.visibility = View.GONE
            },
            onSoftKeyboardOpenListener = { },
            onEmojiPopupShownListener = { },
            onEmojiPopupDismissListener = { },
            onEmojiBackspaceClickListener = { },
        )

        binding.addEmojiIcon.setOnClickListener {
            emojiPopup.show()
        }
    }

    private fun initAdapter() {
        daysAdapter = DaysAdapter(this)
        binding.daysRv.adapter = daysAdapter
    }


}