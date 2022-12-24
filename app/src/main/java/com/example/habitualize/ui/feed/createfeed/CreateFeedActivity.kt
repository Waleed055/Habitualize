package com.example.habitualize.ui.feed.createfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityCreateFeedBinding
import com.example.habitualize.ui.models.FeedModel
import com.example.habitualize.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateFeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateFeedBinding
    private val viewModel: CreateFeedViewModel by viewModels()

    private var selectedLange = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateFeedBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        selectedLange = SharePrefHelper.readString(languageCode).toString()


        initSpinner()
        initListeners()
        initObserver()
    }

    private fun initObserver(){
        viewModel.isFeedUploaded.observe(this){
            if(it){
                Toast.makeText(this, resources.getString(R.string.uploaded), Toast.LENGTH_SHORT)
                    .show()
                Handler(Looper.myLooper()!!).postDelayed({finish()},500)
            }else{
                Toast.makeText(this, resources.getString(R.string.uploaded_error), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initListeners() {
        binding.cvSaveBtn.setOnClickListener {
            if (checkFields()) {
                var feedModel = FeedModel(
                    user_id = SharePrefHelper.readString(userId).toString(),
                    user_name = SharePrefHelper.readString(userName).toString(),
                    user_image = SharePrefHelper.readString(userImage).toString(),
                    question = binding.etFeedQuestion.text.toString(),
                    answer = binding.etFeedAnswer.text.toString(),
                    created_at = "${ Calendar.getInstance().timeInMillis }", //This method returns the time in millis
                    language_code = selectedLange
                )
                viewModel.createFeed(this, feedModel)
            }
        }
    }

    private fun initSpinner() {
        binding.spinner.hint = selectedLange
        binding.spinner.setItems(languagesList)
        binding.spinner.setOnItemSelectedListener { view, position, id, item ->
            selectedLange = languageCodeList[position]
        }
    }

    private fun checkFields(): Boolean {
        return if (binding.etFeedQuestion.text.isNotEmpty()) {
            if (binding.etFeedAnswer.text.isNotEmpty()) {
                true
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.possible_ans),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
        } else {
            Toast.makeText(this, resources.getString(R.string.possible_que), Toast.LENGTH_SHORT).show()
            false
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
        binding.root.setBackgroundColor(ContextCompat.getColor(this, color))
        binding.cvSaveBtn.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.spinner.setHintColor(ContextCompat.getColor(this, color))
    }

}