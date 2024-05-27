package com.dicoding.suargaapp.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import com.dicoding.suargaapp.databinding.ActivityOnBoardingPageStartBinding
import com.dicoding.suargaapp.ui.asesmen.AsesmenActivity

class OnBoardingPageStart : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingPageStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingPageStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.buttonNext.setOnClickListener {
            val intent = Intent(this, OnBoardingPageMid::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()
            startActivity(intent, options)
        }
    }
}