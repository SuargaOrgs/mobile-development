package com.dicoding.suargaapp.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityLoginBinding
import com.dicoding.suargaapp.databinding.ActivityMainBinding
import com.dicoding.suargaapp.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction(){

    }
}