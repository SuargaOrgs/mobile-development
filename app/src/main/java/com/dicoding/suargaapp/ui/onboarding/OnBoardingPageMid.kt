package com.dicoding.suargaapp.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityOnBoardingPageMidBinding
import com.dicoding.suargaapp.databinding.ActivityOnBoardingPageStartBinding
import com.dicoding.suargaapp.ui.signup.SignupActivity

class OnBoardingPageMid : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingPageMidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingPageMidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.myButton.setOnClickListener {
            val intent = Intent(this, OnBoardingPageEnd::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()
            startActivity(intent, options)
        }

        binding.myButtonSkip.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()
            startActivity(intent, options)
        }
    }
}