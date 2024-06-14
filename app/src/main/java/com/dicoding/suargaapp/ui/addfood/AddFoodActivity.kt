package com.dicoding.suargaapp.ui.addfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.data.remote.response.Food
import com.dicoding.suargaapp.databinding.ActivityAddFoodBinding
import com.dicoding.suargaapp.databinding.ActivityAsesmenBinding
import com.dicoding.suargaapp.ui.camera.CameraActivity
import com.dicoding.suargaapp.ui.resultscan.ResultScanActivity
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory

class AddFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFoodBinding
    private val addFoodViewModel by viewModels<AddFoodViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    private var foodList: List<Food> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodNamelEditText = binding.foodNameEditText
        val portionEditText = binding.portionEditText

        foodNamelEditText.setParentLayout(binding.foodNameEditTextLayout)
        portionEditText.setParentLayout(binding.portionEditTextLayout)

        addFoodViewModel.foodList.observe(this){ foods ->
            foodList = foods
        }

        setupAction()

    }

    private fun setupAction() {
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.buttonSave.setOnClickListener{
            val foodName = binding.foodNameEditText.text.toString()
            val portion = binding.portionEditText.text.toString()
            if (foodName.isNotEmpty() && portion.isNotEmpty()) {
                checkFood(foodName)
            } else {
                Toast.makeText(this, "Mohon isi semua field", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonCancel.setOnClickListener{
            val intent = Intent(this, ResultScanActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkFood(foodName: String) {
        val food = foodList.find { it.namaMakanan.equals(foodName, ignoreCase = true) }
        if (food != null) {
            displayFoodNutrients(food)
        } else {
            Toast.makeText(this, "Mohon maaf, makanan yang diinputkan belum tersedia di database aplikasi", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayFoodNutrients(food: Food) {
        val nameFood = food.namaMakanan
        val carbohydrate = food.karbohidrat.toString()
        val protein = food.protein.toString()
        val fat = food.lemak.toString()
        val vitamin = food.vitamin

        val intent = Intent(this, ResultScanActivity::class.java)
        intent.putExtra("nameFood", nameFood)
        intent.putExtra("carbohydrate", carbohydrate)
        intent.putExtra("protein", protein)
        intent.putExtra("fat", fat)
        intent.putExtra("vitamin", vitamin)

        startActivity(intent)
    }
}