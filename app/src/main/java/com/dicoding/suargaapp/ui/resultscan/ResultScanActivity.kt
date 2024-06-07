package com.dicoding.suargaapp.ui.resultscan

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.suargaapp.databinding.ActivityResultScanBinding
import com.dicoding.suargaapp.ui.addfood.AddFoodActivity
import com.dicoding.suargaapp.ui.camera.CameraActivity

class ResultScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultScanBinding
    private val foodList = mutableListOf<String>()
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodNamelEditText = binding.foodNameEditText
        val buttonPagi = binding.buttonPagi
        val buttonSiang = binding.buttonSiang
        val buttonMalam = binding.buttonMalam

        foodNamelEditText.setParentLayout(binding.foodNameEditTextLayout)

        val buttons = listOf(buttonPagi, buttonSiang, buttonMalam)

        buttonPagi.setOnClickListener { selectButton(buttonPagi, buttons) }
        buttonSiang.setOnClickListener { selectButton(buttonSiang, buttons) }
        buttonMalam.setOnClickListener { selectButton(buttonMalam, buttons) }

        adapter = FoodAdapter(foodList, { position ->
            Toast.makeText(this, "Edit item at position: $position", Toast.LENGTH_SHORT).show()
        }, { position ->
            foodList.removeAt(position)
            adapter.notifyItemRemoved(position)
        })

        binding.rvFoodList.layoutManager = LinearLayoutManager(this)
        binding.rvFoodList.adapter = adapter

        binding.btnAddFood.setOnClickListener {
            val intent = Intent(this, AddFoodActivity::class.java)
            startActivity(intent)
            // Kode menambahkan makanan sementara
            val newFood = "Nama Makanan"
            foodList.add(newFood)
            adapter.notifyItemInserted(foodList.size - 1)
        }

        val imageUriString = intent.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)
        imageUriString?.let {
            val imageUri = Uri.parse(it)
            binding.ivImgMakanan.setImageURI(imageUri)
        }

        setupAction()
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
}
