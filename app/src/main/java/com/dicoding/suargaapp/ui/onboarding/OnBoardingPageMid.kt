package com.dicoding.suargaapp.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityOnBoardingPageMidBinding
import com.dicoding.suargaapp.databinding.ActivityOnBoardingPageStartBinding

class OnBoardingPageMid : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingPageMidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingPageMidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.myButton.setOnClickListener{
            startActivity(Intent(this, OnBoardingPageEnd::class.java))
        }
    }
}