package com.dicoding.suargaapp.ui.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityResultBinding
import com.dicoding.suargaapp.helper.Helper.calculateAge
import com.dicoding.suargaapp.helper.Helper.calculatePregnancyAge
import com.dicoding.suargaapp.ui.main.MainActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }
    private fun setupAction() {
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        val height = intent.getIntExtra("height", 0)
        val weight = intent.getIntExtra("weight", 0)
        val activity = intent.getStringExtra("activity")
        val stress = intent.getStringExtra("stress")
        val carbohydrateNeeds = intent.getIntExtra("carbohydrateNeeds", 0)
        val proteinNeeds = intent.getIntExtra("proteinNeeds", 0)
        val fatNeeds = intent.getIntExtra("fatNeeds", 0)


        binding.tvHeight.text = getString(R.string.height_text, height)
        binding.tvWeight.text = getString(R.string.weight_text, weight)
        binding.tvMomActivity.text = activity
        binding.tvStressFactor.text = stress
        binding.tvCarbohydrate.text = carbohydrateNeeds.toString()
        binding.tvProtein.text = proteinNeeds.toString()
        binding.tvFat.text = fatNeeds.toString()

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}