package com.dicoding.suargaapp.ui.resultscan

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.suargaapp.adapter.FoodAdapter
import com.dicoding.suargaapp.data.remote.response.Food
import com.dicoding.suargaapp.data.remote.response.UploadResponse
import com.dicoding.suargaapp.databinding.ActivityResultScanBinding
import com.dicoding.suargaapp.ui.addfood.AddFoodActivity
import com.dicoding.suargaapp.ui.camera.CameraActivity
import com.dicoding.suargaapp.ui.camera.CameraActivity.Companion.EXTRA_CAMERAX_IMAGE
import com.dicoding.suargaapp.ui.main.MainActivity
import com.dicoding.suargaapp.utils.reduceFileImage
import com.dicoding.suargaapp.utils.uriToFile
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class ResultScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultScanBinding
    private val foodList = mutableListOf<Food>()
    private lateinit var adapter: FoodAdapter
    private val resultScanViewModel by viewModels<ResultScanViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        val foodName = intent.getStringExtra("nameFood")
        val portion = intent.getIntExtra("portion", 0)
        val carbohydrate = intent.getDoubleExtra("carbohydrate", 0.0)
        val protein = intent.getDoubleExtra("protein", 0.0)
        val fat = intent.getDoubleExtra("fat", 0.0)
        val vitamin = intent.getStringExtra("vitamin")

        if (id != 0 && foodName != null) {
            val newFood = Food(
                namaMakanan = foodName,
                karbohidrat = carbohydrate,
                protein = protein,
                lemak = fat,
                createdAt = null,
                vitamin = vitamin,
                id = id
            )

            foodList.add(newFood)
            binding.tvTotalCarboValue.text = "$carbohydrate gr"
            binding.tvTotalLemakValue.text = "$fat gr"
            binding.tvTotalProteinValue.text = "$protein gr"
            binding.tvTotalVitaminValue.text = "$vitamin"
            binding.btnAddFood.visibility = View.GONE
        }

        val activityEditText = binding.activityEditText
        val buttonPagi = binding.buttonPagi
        val buttonSiang = binding.buttonSiang
        val buttonMalam = binding.buttonMalam

        activityEditText.setParentLayout(binding.activityEditTextLayout)

        val buttons = listOf(buttonPagi, buttonSiang, buttonMalam)

        buttonPagi.setOnClickListener { selectButton(buttonPagi, buttons) }
        buttonSiang.setOnClickListener { selectButton(buttonSiang, buttons) }
        buttonMalam.setOnClickListener { selectButton(buttonMalam, buttons) }

        val imageUriString = intent.getStringExtra(EXTRA_CAMERAX_IMAGE)
        imageUriString?.let {
            val imageUri = Uri.parse(it)
            binding.ivImgMakanan.setImageURI(imageUri)
        }

        binding.btnAddFood.setOnClickListener {
            val intent = Intent(this, AddFoodActivity::class.java).apply {
                putExtra(EXTRA_CAMERAX_IMAGE, imageUriString)
            }
            startActivity(intent)
        }

        binding.buttonSave.setOnClickListener {
            Log.d("ResultScanActivity", "Save button clicked")
            if (imageUriString != null) {
                val imageUri = Uri.parse(imageUriString)
                Log.d("ResultScanActivity", "Image URI is not null")
                val imageFile = uriToFile(imageUri, this).reduceFileImage()
                val namaAktivitas = activityEditText.text.toString()
                val waktuMakan = if (buttonPagi.isSelected) "Pagi" else if (buttonSiang.isSelected) "Siang" else "Malam"

                val requestAktivitas = namaAktivitas.toRequestBody("text/plain".toMediaType())
                val requestWaktuMakan = waktuMakan.toRequestBody("text/plain".toMediaType())
                val requestPorsi = portion.toString().toRequestBody("text/plain".toMediaType())
                val requestMakanan = id.toString().toRequestBody("text/plain".toMediaType())
                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())

                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "image",
                    imageFile.name,
                    requestImageFile
                )

                showLoading(true)

                lifecycleScope.launch {
                    Log.d("ResultScanActivity", "Launching coroutine for upload")
                    try {
                        val response = resultScanViewModel.upload(
                            imageMultipart,
                            requestAktivitas,
                            requestWaktuMakan,
                            requestMakanan,
                            requestPorsi
                        )

                        response.message?.let { Toast.makeText(this@ResultScanActivity, it, Toast.LENGTH_SHORT).show() }

                        val intent = Intent(this@ResultScanActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } catch (e: HttpException) {
                        val errorBody = e.response()?.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, UploadResponse::class.java)
                        errorResponse.message?.let { message ->
                            Toast.makeText(this@ResultScanActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    } finally {
                        showLoading(false)
                    }
                }
            } else {
                Log.d("ResultScanActivity", "Image URI is null")
                Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show()
            }
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
            binding.tvTotalCarboValue.text = "0.0 gr"
            binding.tvTotalLemakValue.text = "0.0 gr"
            binding.tvTotalProteinValue.text = "0.0 gr"
            binding.tvTotalVitaminValue.text = "-"
            binding.btnAddFood.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
