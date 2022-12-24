package com.example.habitualize.ui.challenge

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityChallengeBinding
import com.example.habitualize.ui.challenge.adapter.DaysAdapter
import com.example.habitualize.ui.models.CommunityChallengeModel
import com.example.habitualize.utils.*
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.EmojiPopup
import com.vanniktech.emoji.google.GoogleEmojiProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeActivity : AppCompatActivity() {
    private val viewModel: ChallengeViewModel by viewModels()
    lateinit var binding: ActivityChallengeBinding
    lateinit var daysAdapter: DaysAdapter
    private var tasksList = arrayListOf<String>()
    private lateinit var challengeData: CommunityChallengeModel
    private var whichScreen = 0
    private var selectedLanguage = ""
    private var challenge_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EmojiManager.install(GoogleEmojiProvider())
        binding = ActivityChallengeBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        binding.tvTitle.text = resources.getString(R.string.create)
        setContentView(binding.root)
        whichScreen = intent.getIntExtra("whichScreen", 0)

        if(whichScreen == 2){ // reached for edit
            binding.tvCreateChallenge.text = "Update"
            challenge_id = intent.getStringExtra("challenge_id").toString()
            viewModel.getChallengeData(this,challenge_id)
        }else{
            binding.tvCreateChallenge.text = resources.getString(R.string.create)
        }


        initAdapter()
        initListener()
        initSpinner()
        initObserver()
    }

    private fun initObserver(){
        viewModel.isTaskAdded.observe(this){
            if (it) finish() else Toast.makeText(
                this,
                resources.getString(R.string.try_again),
                Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.challengeData.observe(this){
            challengeData = it
            setData(it)
        }

    }

    private fun initListener() {
        val emojiPopup = EmojiPopup(
            binding.rootView,
            binding.challengeEmoji,
            onSoftKeyboardCloseListener = {
                if (binding.challengeEmoji.text.toString().isEmpty()) {
//                    binding.addEmojiIcon.visibility = View.VISIBLE
                }
            },
            onEmojiClickListener = {
                println("===EMOJI==== ${it.unicode}")
//                binding.addEmojiIcon.visibility = View.GONE
            },
            onSoftKeyboardOpenListener = { },
            onEmojiPopupShownListener = { },
            onEmojiPopupDismissListener = { },
            onEmojiBackspaceClickListener = { },
        )

        binding.challengeEmoji.setOnClickListener {
            emojiPopup.show()
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.cvCreateBtn.setOnClickListener {
            if (!binding.challengeName.text.isNullOrEmpty()){
                if (!binding.challengeTextInputEditText.text.isNullOrEmpty()){
                    if (!binding.challengeEmoji.text.isNullOrEmpty()){
                        when (whichScreen) {
                            0 -> { // create local task
                                viewModel.createNewChallenge(
                                    activity = this,
                                    challenge_name = binding.challengeName.text.toString(),
                                    challenge_description = binding.challengeTextInputEditText.text.toString(),
                                    challenge_emoji = binding.challengeEmoji.text.toString(),
                                    tasksList = tasksList,
                                    language_code = selectedLanguage
                                )


                                //----------------------------------------------------------------------
                                //counter
                                var _counter  = SharePrefHelper.readInteger(addTaskCounter) + 1
                                SharePrefHelper.writeInteger(addTaskCounter, _counter)

                            }
                            1 -> { // create community challenge
                                viewModel.createCommunityChallenge(
                                    this,
                                    CommunityChallengeModel(
                                        user_id = SharePrefHelper.readString(userId).toString(),
                                        user_name = SharePrefHelper.readString(userName).toString(),
                                        challenge_name = binding.challengeName.text.toString(),
                                        challenge_description = binding.challengeTextInputEditText.text.toString(),
                                        challenge_emoji = binding.challengeEmoji.text.toString(),
                                        challenge_list = tasksList,
                                        language_code = selectedLanguage
                                    )
                                )

                                //----------------------------------------------------------------------
                                //counter
                                var _counter  = SharePrefHelper.readInteger(
                                    addCommunityChallengeCounter) + 1
                                SharePrefHelper.writeInteger(addCommunityChallengeCounter, _counter)

                            }
                            2 -> {
                                viewModel.updateCommunityChallenge(
                                    this,
                                    CommunityChallengeModel(
                                        user_id = SharePrefHelper.readString(userId).toString(),
                                        user_name = SharePrefHelper.readString(userName).toString(),
                                        challenge_id = challenge_id,
                                        challenge_name = binding.challengeName.text.toString(),
                                        challenge_description = binding.challengeTextInputEditText.text.toString(),
                                        challenge_emoji = binding.challengeEmoji.text.toString(),
                                        challenge_list = tasksList,
                                        language_code = selectedLanguage
                                    )
                                )
                            }
                        }
                    }else{
                        Toast.makeText(this, resources.getString(R.string.select_emoji), Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, resources.getString(R.string.enter_description), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, resources.getString(R.string.enter_challenge_name), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAdapter() {
        for(i in 1..30){
            tasksList.add(resources.getString(R.string.empty_task))
        }
        daysAdapter = DaysAdapter(this, object: DaysAdapter.CallBacks{
            override fun onSaveTaskEvent(position: Int, task: String) {
                tasksList[position] = task
            }
        },SharePrefHelper.readInteger(selectedColorIndex))
        binding.daysRv.adapter = daysAdapter
    }

    private fun initSpinner(){
        binding.spinner.setItems(languagesList)
        binding.spinner.setOnItemSelectedListener { view, position, id, item ->
            selectedLanguage = languageCodeList[position]
        }
    }

    private fun setData(data: CommunityChallengeModel){
        binding.challengeName.setText(data.challenge_name)
        binding.challengeEmoji.setText(data.challenge_emoji)
//        binding.addEmojiIcon.visibility = View.GONE
        binding.challengeTextInputEditText.setText(data.challenge_description)
        selectedLanguage = data.language_code
        var index = 0
        for (i in 0 until languageCodeList.size){
            if(data.language_code == languageCodeList[i]){
                index = i
                break
            }
        }
        binding.spinner.selectedIndex = index
        daysAdapter.challenges = data.challenge_list
        tasksList = data.challenge_list
        daysAdapter.notifyDataSetChanged()
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
        binding.cvCreateBtn.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.rootView.setBackgroundColor(ContextCompat.getColor(this, color))
        binding.outlinedTextField.boxStrokeColor = ContextCompat.getColor(this, color)
        binding.outlinedTextField.hintTextColor = ColorStateList.valueOf(color)
        binding.spinner.setHintColor(ContextCompat.getColor(this, color))
        binding.outlinedTextField.boxStrokeColor = ContextCompat.getColor(this, R.color.hint_color)
        binding.outlinedTextField.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.hint_color))
    }

}