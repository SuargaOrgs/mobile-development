package com.dicoding.suargaapp.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivitySignupBinding
import com.dicoding.suargaapp.databinding.ActivitySignupNextBinding

class SignupNextActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupNextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupNextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}