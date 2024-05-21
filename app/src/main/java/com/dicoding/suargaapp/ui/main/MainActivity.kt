package com.dicoding.suargaapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dicoding.suargaapp.databinding.ActivityMainBinding
import com.dicoding.suargaapp.ui.onboarding.OnBoardingPageStart
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnBoardingPageStart::class.java))
                finish()
            } else {
                val greetings = "Hai, ${user.name}"
                binding.greetingTextView.text = greetings
            }
        }

        setupAction()
    }

    private fun setupAction() {
        binding.myButton.setOnClickListener{
            mainViewModel.logout()
        }
    }
}