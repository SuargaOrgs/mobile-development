package com.dicoding.suargaapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.suargaapp.databinding.ActivityMainBinding
import com.dicoding.suargaapp.ui.onboarding.OnBoardingPageStart

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.myButton.setOnClickListener{
            startActivity(Intent(this, OnBoardingPageStart::class.java))
        }
    }
}