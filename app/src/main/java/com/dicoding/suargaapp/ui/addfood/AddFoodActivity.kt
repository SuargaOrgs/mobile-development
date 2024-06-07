package com.dicoding.suargaapp.ui.addfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityAddFoodBinding
import com.dicoding.suargaapp.databinding.ActivityAsesmenBinding
import com.dicoding.suargaapp.ui.camera.CameraActivity
import com.dicoding.suargaapp.ui.resultscan.ResultScanActivity

class AddFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodNamelEditText = binding.foodNameEditText
        val portionEditText = binding.portionEditText

        foodNamelEditText.setParentLayout(binding.foodNameEditTextLayout)
        portionEditText.setParentLayout(binding.portionEditTextLayout)


        setupAction()

    }

    private fun setupAction() {
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.buttonSave.setOnClickListener{
            val intent = Intent(this, ResultScanActivity::class.java)
            startActivity(intent)
        }
        binding.buttonCancel.setOnClickListener{
            val intent = Intent(this, ResultScanActivity::class.java)
            startActivity(intent)
        }
    }
}