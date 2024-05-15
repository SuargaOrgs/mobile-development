package com.dicoding.suargaapp.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityMainBinding
import com.dicoding.suargaapp.databinding.ActivityOnBoardingPageStartBinding

class OnBoardingPageStart : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingPageStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingPageStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.myButton.setOnClickListener{
            startActivity(Intent(this, OnBoardingPageMid::class.java))
        }
    }
}