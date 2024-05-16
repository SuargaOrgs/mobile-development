package com.dicoding.suargaapp.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityLoginBinding
import com.dicoding.suargaapp.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction(){

    }
}