package com.dicoding.suargaapp.ui.editpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityDetailProfileBinding
import com.dicoding.suargaapp.databinding.ActivityEditPasswordBinding
import com.dicoding.suargaapp.ui.detailprofile.DetailProfileActivity
import com.dicoding.suargaapp.ui.main.MainActivity

class EditPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val oldPassEditText = binding.oldPasswordEditText
        val newPassEditText = binding.newPasswordEditText


        oldPassEditText.setParentLayout(binding.oldPasswordEditTextLayout)
        newPassEditText.setParentLayout(binding.newPasswordEditTextLayout)

        setupAction()
    }

    private fun setupAction() {

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSave.setOnClickListener {
            Toast.makeText(this, "Fitur masih dalam proses pengembangan", Toast.LENGTH_SHORT).show()
        }
    }
}