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
        val birthday = intent.getStringExtra("birthday")
        val age = calculateAge(birthday ?: "1991-01-24")
        val activity = intent.getStringExtra("activity")
        val stress = intent.getStringExtra("stress")

        Log.d("cek", "age: $age")
        val bmr = calculateBMR(height, weight, age)
        val tee = calculateTEE(bmr)
        val proteinNeeds = calculateProtein(tee)
        val fatNeeds = calculateFat(tee)
        val carbohydrateNeeds = calculateCarbohydrate(tee)

        binding.tvHeight.text = getString(R.string.height_text, height)
        binding.tvWeight.text = getString(R.string.weight_text, weight)
        binding.tvMomActivity.text = activity
        binding.tvStressFactor.text = stress
        binding.tvCarbohydrate.text = carbohydrateNeeds.toInt().toString()
        binding.tvProtein.text = proteinNeeds.toInt().toString()
        binding.tvFat.text = fatNeeds.toInt().toString()

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun calculateBMR(height: Int, weight: Int, age: Int): Double {
        val bmr = 655 + (9.6 * weight) + (1.85 * height) - (4.68 * age)
        Log.d("cek", "bmr: $bmr")
        return bmr
    }

    private fun calculateTEE(bmr: Double): Double {
        val activity = intent.getStringExtra("activity")
        val stress = intent.getStringExtra("stress")


        val activityFactor = when (activity) {
            "Istirahat bed rest" -> 1.1
            "Bed rest, tapi bisa bergerak terbatas" -> 1.2
            "Turun dari tempat tidur" -> 1.3
            "Aktivitas sedang" -> 1.4
            "Aktivitas berat" -> 1.75
            else -> 0.0
        }

        val stressFactor = when (stress) {
            "Tidak ada stress" -> 1.0
            "Stress ringan" -> 1.1
            "Stress sedang" -> 1.2
            "Stress berat" -> 1.3
            "Stress sangat berat" -> 1.4
            else -> 0.0
        }

        val pregnacyDate = intent.getStringExtra("pregnancyDate")
        val pregnancyAge = calculatePregnancyAge(pregnacyDate ?: "2024-01-09")
        Log.d("cek", "pregnacyAge: $pregnancyAge")

        if (pregnancyAge > 12) {
            val tee = (bmr * activityFactor * stressFactor) + 300
            Log.d("cek", "tee: $tee")
            return tee
        } else {
            val tee = bmr * activityFactor * stressFactor
            Log.d("cek", "tee: $tee")
            return tee
        }
    }

    private fun calculateProtein(tee: Double): Double {
        val pregnacyDate = intent.getStringExtra("pregnancyDate")
        val pregnancyAge = calculatePregnancyAge(pregnacyDate ?: "2024-01-09")

        val protein = (15.0 / 100.0) * tee
        if (pregnancyAge > 12) {
            val proteinNeeds = (protein / 4.0) + 10
            Log.d("cek", "proteinNeeds: $proteinNeeds")
            return proteinNeeds
        } else {
            val proteinNeeds = (protein / 4.0)
            Log.d("cek", "proteinNeeds: $proteinNeeds")
            return proteinNeeds
        }
    }

    private fun calculateFat(tee: Double): Double {
        val pregnacyDate = intent.getStringExtra("pregnancyDate")
        val pregnancyAge = calculatePregnancyAge(pregnacyDate ?: "2024-01-09")

        val fat = (25.0 / 100.0) * tee
        if (pregnancyAge > 12) {
            val fatNeeds = (fat / 9.0) + 2.3
            Log.d("cek", "fatNeeds: $fatNeeds")
            return fatNeeds
        } else {
            val fatNeeds = (fat / 9.0)
            Log.d("cek", "fatNeeds: $fatNeeds")
            return fatNeeds
        }
    }

    private fun calculateCarbohydrate(tee: Double): Double {
        val pregnacyDate = intent.getStringExtra("pregnancyDate")
        val pregnancyAge = calculatePregnancyAge(pregnacyDate ?: "2024-01-09")

        val carbohydrate = (60.0 / 100.0) * tee
        if (pregnancyAge > 12) {
            val carbohydrateNeeds = (carbohydrate / 4.0) + 40
            Log.d("cek", "carbohydrateNeeds: $carbohydrateNeeds")
            return carbohydrateNeeds
        } else {
            val carbohydrateNeeds = (carbohydrate / 4.0)
            Log.d("cek", "carbohydrateNeeds: $carbohydrateNeeds")
            return carbohydrateNeeds
        }
    }
}