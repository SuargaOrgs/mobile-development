package com.dicoding.suargaapp.ui.resultscan

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.suargaapp.adapter.FoodAdapter
import com.dicoding.suargaapp.data.remote.response.Food
import com.dicoding.suargaapp.databinding.ActivityResultScanBinding
import com.dicoding.suargaapp.ui.addfood.AddFoodActivity
import com.dicoding.suargaapp.ui.camera.CameraActivity

class ResultScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultScanBinding
    private val foodList = mutableListOf<Food>()
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodName = intent.getStringExtra("nameFood")
        val carbohydrate = intent.getStringExtra("carbohydrate")
        val protein = intent.getStringExtra("protein")
        val fat = intent.getStringExtra("fat")
        val vitamin = intent.getStringExtra("vitamin")

        if (foodName != null && carbohydrate != null && protein != null && fat != null && vitamin != null) {
            val newFood = Food(
                namaMakanan = foodName,
                karbohidrat = carbohydrate.toDoubleOrNull(),
                protein = protein.toDoubleOrNull(),
                lemak = fat.toDoubleOrNull(),
                createdAt = null,
                vitamin = vitamin,
                id = null
            )

            foodList.add(newFood)
            binding.tvTotalCarboLabel.text = "Karbohidrat"
            binding.tvTotalCarboValue.text = "$carbohydrate gr"
            binding.tvTotalLemakLabel.text = "Lemak"
            binding.tvTotalLemakValue.text = "$fat gr"
            binding.tvTotalProteinLabel.text = "Protein"
            binding.tvTotalProteinValue.text = "$protein gr"
            binding.tvTotalVitaminLabel.text = "Vitamin"
            binding.tvTotalVitaminValue.text = "$vitamin"
            binding.btnAddFood.visibility = View.GONE
        }

        val foodNameEditText = binding.foodNameEditText
        val buttonPagi = binding.buttonPagi
        val buttonSiang = binding.buttonSiang
        val buttonMalam = binding.buttonMalam

        foodNameEditText.setParentLayout(binding.foodNameEditTextLayout)

        val buttons = listOf(buttonPagi, buttonSiang, buttonMalam)

        buttonPagi.setOnClickListener { selectButton(buttonPagi, buttons) }
        buttonSiang.setOnClickListener { selectButton(buttonSiang, buttons) }
        buttonMalam.setOnClickListener { selectButton(buttonMalam, buttons) }

        binding.btnAddFood.setOnClickListener {
            val intent = Intent(this, AddFoodActivity::class.java)
            startActivity(intent)
        }

        val imageUriString = intent.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)
        imageUriString?.let {
            val imageUri = Uri.parse(it)
            binding.ivImgMakanan.setImageURI(imageUri)
        }

        setupRecyclerView()
        setupAction()
    }

    private fun setupRecyclerView() {
        adapter = FoodAdapter(foodList, { position ->
            Toast.makeText(this, "Edit item at position: $position", Toast.LENGTH_SHORT).show()
        }, { position ->
            foodList.removeAt(position)
            adapter.notifyItemRemoved(position)
            updateNutritionValues()
        })

        binding.rvFoodList.layoutManager = LinearLayoutManager(this)
        binding.rvFoodList.adapter = adapter
    }

    private fun setupAction() {

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.buttonCancel.setOnClickListener{
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }

    private fun selectButton(selectedButton: Button, buttons: List<Button>) {
        val selectedTextColor = ContextCompat.getColor(this, android.R.color.white)
        val defaultTextColor = ContextCompat.getColor(this, android.R.color.black)

        buttons.forEach { button ->
            button.isSelected = button == selectedButton
            button.setTextColor(if (button.isSelected) selectedTextColor else defaultTextColor)
        }
    }

    private fun updateNutritionValues() {
        if (foodList.isEmpty()) {
            binding.tvTotalCarboValue.text = "0.00 gr"
            binding.tvTotalLemakValue.text = "0.00 gr"
            binding.tvTotalProteinValue.text = "0.00 gr"
            binding.tvTotalVitaminValue.text = "-"
            binding.btnAddFood.visibility = View.VISIBLE // Tampilkan tombol tambah makanan kembali jika daftar makanan kosong
        } else {
            binding.tvTotalCarboValue.text = foodList.sumByDouble { it.karbohidrat ?: 0.0 }.toString()
        }
    }
}
