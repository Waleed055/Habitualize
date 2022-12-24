package com.example.habitualize.ui.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.habitualize.R
import com.example.habitualize.databinding.ActivityLoginBinding
import com.example.habitualize.ui.auth.AuthViewModel
import com.example.habitualize.ui.auth.signup.SignUpActivity
import com.example.habitualize.ui.home.HomeActivity
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.changeStatusBarColor
import com.example.habitualize.utils.selectedColorIndex
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        viewModel.getAllUserName(this)
        viewModel.getData(this)

        initObservers()
        initListeners()
    }

    private fun initObservers(){
        viewModel.isLoginSuccessful.observe(this){
            if(it){
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, resources.getString(R.string.invalid_username_pw), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initListeners(){
        binding.confirmCard.setOnClickListener {
            if(checkUserDetails()){
                viewModel.doLogin(
                    this,
                    binding.etUserName.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }
        binding.tvSignupBtn.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }

    private fun checkUserDetails(): Boolean{
        return if(binding.etUserName.text.isNotEmpty()){
            if(binding.etPassword.text.isNotEmpty()){
                if(binding.etPassword.text.length > 5){
                    true
                }else{
                    Toast.makeText(this, resources.getString(R.string.short_pw_error), Toast.LENGTH_SHORT).show()
                    false
                }
            }else{
                Toast.makeText(this, resources.getString(R.string.enter_pw), Toast.LENGTH_SHORT).show()
                false
            }
        }else{
            Toast.makeText(this, resources.getString(R.string.user_name_error), Toast.LENGTH_SHORT).show()
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
        binding.appIcon.setColorFilter(ContextCompat.getColor(this, color))
        binding.appName.setTextColor(ContextCompat.getColor(this, color))
        binding.text1.setTextColor(ContextCompat.getColor(this, color))
        binding.root.setBackgroundColor(ContextCompat.getColor(this, color))
        binding.card1.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.card2.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.confirmCard.setCardBackgroundColor(ContextCompat.getColor(this, color))
    }
}