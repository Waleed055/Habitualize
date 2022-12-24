package com.example.habitualize.ui.auth.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivitySignUpBinding
import com.example.habitualize.ui.auth.AuthViewModel
import com.example.habitualize.ui.auth.login.LoginActivity
import com.example.habitualize.ui.home.HomeActivity
import com.example.habitualize.ui.introscreens.IntroActivity
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.changeStatusBarColor
import com.example.habitualize.utils.selectedColorIndex
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getAllUserName(this)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.isSignUpSuccessful.observe(this) {
            if (it) {
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.sign_up_failure),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initListeners() {
        binding.confirmCard.setOnClickListener {
            if (checkUserDetails()) {
                viewModel.doSignUp(
                    this,
                    binding.etUserName.text.toString(),
                    binding.etFirstName.text.toString(),
                    binding.etLastName.text.toString(),
                    binding.etPassword.text.toString(),
                )
            }
        }

        binding.tvLoginButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun checkUserDetails(): Boolean {
        return if (binding.etUserName.text.isNotEmpty()) {
            if (!viewModel.userNameList.contains(binding.etUserName.text.toString())) {
                if (binding.etFirstName.text.isNotEmpty()) {
                    if (binding.etLastName.text.isNotEmpty()) {
                        if (binding.etPassword.text.isNotEmpty()) {
                            if (binding.etPassword.text.length > 5) {
                                if (binding.etConfirmPassword.text.isNotEmpty()) {
                                    if (binding.etPassword.text.toString() == binding.etConfirmPassword.text.toString()) {
                                        true
                                    } else {
                                        Toast.makeText(
                                            this,
                                            resources.getString(R.string.confirm_pw_error),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        false
                                    }
                                } else {
                                    Toast.makeText(
                                        this,
                                        resources.getString(R.string.confirm2_pw_error),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    false
                                }
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
                                resources.getString(R.string.enter_pw)

                                , Toast.LENGTH_SHORT
                            )
                                .show()
                            false
                        }
                    } else {
                        Toast.makeText(this, resources.getString(R.string.last_name_error), Toast.LENGTH_SHORT)
                            .show()
                        false
                    }
                } else {
                    Toast.makeText(this,resources.getString(R.string.first_name_error), Toast.LENGTH_SHORT)
                        .show()
                    false
                }
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.user_already_exist_error),
                    Toast.LENGTH_SHORT
                ).show()
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
        binding.appIcon.setColorFilter(ContextCompat.getColor(this, color))
        binding.appName.setTextColor(ContextCompat.getColor(this, color))
        binding.text1.setTextColor(ContextCompat.getColor(this, color))
        binding.root.setBackgroundColor(ContextCompat.getColor(this, color))
        binding.card1.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.card2.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.card3.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.card4.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.card5.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.confirmCard.setCardBackgroundColor(ContextCompat.getColor(this, color))
    }

}