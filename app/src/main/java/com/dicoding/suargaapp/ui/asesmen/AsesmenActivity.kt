package com.dicoding.suargaapp.ui.asesmen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
            val height = binding.heightEditText.text.toString().toInt()
            val weight = binding.weightEditText.text.toString().toInt()
            val activity = binding.optionActivity.getSelectedOption()
            val stress = binding.optionFact.getSelectedOption()
            val birthday = intent.getStringExtra("birthday")
            val pregnancyDate = intent.getStringExtra("pregnancyDate")

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            intent.putExtra("activity", activity)
            intent.putExtra("stress", stress)
            intent.putExtra("birthday", birthday)
            intent.putExtra("pregnancyDate", pregnancyDate)
            startActivity(intent)
        }
    }


}