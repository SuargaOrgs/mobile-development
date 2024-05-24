package com.dicoding.suargaapp.ui.asesmen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.suargaapp.customview.NumberEditText
import com.dicoding.suargaapp.databinding.ActivityAsesmenBinding
import com.dicoding.suargaapp.ui.result.ResultActivity
import com.dicoding.suargaapp.ui.signup.SignupActivity

class AsesmenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAsesmenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsesmenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val heightEditText: NumberEditText = binding.heightEditText
        val weightEditText: NumberEditText = binding.weightEditText

        heightEditText.setParentLayout(binding.heightEditTextLayout)
        weightEditText.setParentLayout(binding.weightEditTextLayout)

        setupAction()
    }

    private fun setupAction() {
        binding.buttonSubmit.setOnClickListener{
            startActivity(Intent(this, ResultActivity::class.java))
        }
    }
}