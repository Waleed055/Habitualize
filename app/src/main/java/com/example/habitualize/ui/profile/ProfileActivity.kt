package com.example.habitualize.ui.profile

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityProfileActivityBinding
import com.example.habitualize.ui.models.UserModel
import com.example.habitualize.utils.*
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileActivityBinding
    private val viewModel: ProfileViewModel by viewModels()
    private var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileActivityBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)

        binding.profileNotificationSwitch.isChecked = SharePrefHelper.readBoolean(isNotificationAllow)
        userName = SharePrefHelper.readString(userId).toString()
        viewModel.getUserData(this, userName)
        binding.includedLayout.tvTitle.text = resources.getString(R.string.profile)
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.user.observe(this) {
            setData(it)
        }
    }

    private fun initListeners() {

        binding.profileNotificationSwitch.setOnClickListener {
            SharePrefHelper.writeBoolean(isNotificationAllow,binding.profileNotificationSwitch.isChecked)
        }

        binding.includedLayout.backButton.setOnClickListener {
            finish()
        }

        binding.ivEditProfileBtn.setOnClickListener {
            ImagePicker.with(this)
                .compress(200)
                .maxResultSize(1080, 1080)
                .crop()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.updateCard.setOnClickListener {
            if (checkUserDetails()) {
                viewModel.updateUserData(
                    this,
                    binding.etUserName.text.toString(),
                    binding.etFirstName.text.toString(),
                    binding.etLastName.text.toString(),
                    binding.etPassword.text.toString(),
                )
            }
        }

        binding.userImage.setOnClickListener {
            showImagePopup()
        }


    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                Glide.with(this).load(fileUri).into(binding.userImage)
                viewModel.updateProfileImage(this, fileUri, userName)
            }
        }

    private fun showImagePopup() {
        val alertDialog = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        var view = factory.inflate(R.layout.item_image_popup, null)
        alertDialog.setView(view)

        var dialog_imageview: ImageView = view.findViewById(R.id.dialog_imageview)
        Glide.with(this).load(SharePrefHelper.readString(userImage)).into(dialog_imageview)
        alertDialog.show()
    }

    private fun setData(user: UserModel) {
        binding.etUserName.setText(user.user_name)
        binding.etFirstName.setText(user.first_name)
        binding.etLastName.setText(user.last_name)
        binding.etPassword.setText(user.password)
        Glide.with(this).load(user.profile_image).into(binding.userImage)

        //update share preference
        SharePrefHelper.writeString(userId, user.user_name)
        SharePrefHelper.writeString(userName, "${user.first_name} ${user.last_name}")
        SharePrefHelper.writeString(userImage, user.profile_image)
    }

    private fun checkUserDetails(): Boolean {
        return if (binding.etUserName.text.isNotEmpty()) {
            if (binding.etFirstName.text.isNotEmpty()) {
                if (binding.etLastName.text.isNotEmpty()) {
                    if (binding.etPassword.text.isNotEmpty()) {
                        if (binding.etPassword.text.length > 5) {
                            true
                        } else {
                            Toast.makeText(
                                this,
                                resources.getString(R.string.short_pw_error),
                                Toast.LENGTH_SHORT
                            ).show()
                            false
                        }
                    } else {
                        Toast.makeText(
                            this,
                            resources.getString(R.string.enter_pw),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        false
                    }
                } else {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.last_name_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    false
                }
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.first_name_error),
                    Toast.LENGTH_SHORT
                )
                    .show()
                false
            }
        } else {
            Toast.makeText(
                this,
                resources.getString(R.string.user_name_error),
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    private fun updateTheme(position: Int) {
        when (position) {
            0 -> {
                setThemeColor(R.color.theme)
            }
            1 -> {
                setThemeColor(R.color.theme_2)
            }
            2 -> {
                setThemeColor(R.color.theme_3)
            }
            3 -> {
                setThemeColor(R.color.theme_4)
            }
            4 -> {
                setThemeColor(R.color.theme_5)
            }
            5 -> {
                setThemeColor(R.color.theme_6)
            }
            6 -> {
                setThemeColor(R.color.theme_7)
            }
            7 -> {
                setThemeColor(R.color.theme_8)
            }
        }
    }

    private fun setThemeColor(color: Int) {
        changeStatusBarColor(this, color)
        binding.root.setBackgroundColor(ContextCompat.getColor(this, color))
        binding.card1.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.card2.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.card3.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.card4.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.updateCard.setCardBackgroundColor(ContextCompat.getColor(this, color))
    }

}