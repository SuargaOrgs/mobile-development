package com.dicoding.suargaapp.ui.resultscan

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityErrorScanBinding
import com.dicoding.suargaapp.databinding.ActivityResultScanBinding
import com.dicoding.suargaapp.ui.camera.CameraActivity
import com.dicoding.suargaapp.ui.camera.CameraActivity.Companion.EXTRA_CAMERAX_IMAGE
import com.dicoding.suargaapp.utils.createCustomTempFile

class ErrorScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityErrorScanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnLogManual.setOnClickListener{
            val imageUriString = intent.getStringExtra(EXTRA_CAMERAX_IMAGE)
            val intent = Intent(this, ResultScanActivity::class.java).apply {
                putExtra(EXTRA_CAMERAX_IMAGE, imageUriString)
            }
            startActivity(intent)
        }
        binding.btnBackScan.setOnClickListener{
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}