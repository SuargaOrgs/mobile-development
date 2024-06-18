package com.dicoding.suargaapp.ui.asesmen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.suargaapp.customview.NumberEditText
import com.dicoding.suargaapp.databinding.ActivityAsesmenBinding
import com.dicoding.suargaapp.helper.Helper.calculateAge
import com.dicoding.suargaapp.helper.Helper.calculatePregnancyAge
import com.dicoding.suargaapp.ui.main.MainActivity
import com.dicoding.suargaapp.ui.result.ResultActivity
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory
import kotlin.math.round

class AsesmenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAsesmenBinding
    private val assessmentViewModel by viewModels<AssessmentViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

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
            val age = calculateAge(birthday ?: "2003-01-24")

            Log.d("cek", "activity: $activity")
            Log.d("cek", "stress: $stress")
            Log.d("cek", "age: $age")

            val bmr = calculateBMR(height, weight, age)
            val tee = calculateTEE(bmr)
            val proteinNeeds = round(calculateProtein(tee)).toInt()
            val fatNeeds = round(calculateFat(tee)).toInt()
            val carbohydrateNeeds = round(calculateCarbohydrate(tee)).toInt()

            assessmentViewModel.saveAssessment(height, weight, activity, stress, carbohydrateNeeds, proteinNeeds, fatNeeds).observe(this) { result ->
                if (result.error == false) {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("height", height)
                    intent.putExtra("weight", weight)
                    intent.putExtra("activity", activity)
                    intent.putExtra("stress", stress)
                    intent.putExtra("proteinNeeds", proteinNeeds)
                    intent.putExtra("fatNeeds", fatNeeds)
                    intent.putExtra("carbohydrateNeeds", carbohydrateNeeds)
                    startActivity(intent)
                    Toast.makeText(this, "Assessment saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }

            assessmentViewModel.isLoading.observe(this) {
                showLoading(it)
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun calculateBMR(height: Int, weight: Int, age: Int): Double {
        val bmr = 655 + (9.6 * weight) + (1.85 * height) - (4.68 * age)
        Log.d("cek", "bmr: $bmr")
        return bmr
    }

    private fun calculateTEE(bmr: Double): Double {
        val activity = binding.optionActivity.getSelectedOption()
        val stress = binding.optionFact.getSelectedOption()


        val activityFactor = when (activity) {
            "Istirahat Bed Rest" -> 1.1
            "Bed Rest dengan aktivitas terbatas" -> 1.2
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}