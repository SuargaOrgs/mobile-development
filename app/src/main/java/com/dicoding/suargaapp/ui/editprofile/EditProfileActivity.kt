package com.dicoding.suargaapp.ui.editprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityDetailProfileBinding
import com.dicoding.suargaapp.databinding.ActivityEditProfileBinding
import com.dicoding.suargaapp.ui.detailprofile.DetailProfileActivity
import com.dicoding.suargaapp.ui.main.MainViewModel
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userNamelEditText = binding.nameEditText
        val emailEditText = binding.emailEditText


        userNamelEditText.setParentLayout(binding.NameEditTextLayout)
        emailEditText.setParentLayout(binding.EmailEditTextLayout)

        setupAction()
        observeViewModel()
    }
    private fun setupAction() {

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            val intent = Intent(this, DetailProfileActivity::class.java)
            startActivity(intent)
        }
        binding.buttonCancel.setOnClickListener {
            val intent = Intent(this, DetailProfileActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSave.setOnClickListener {
            Toast.makeText(this, "Fitur masih dalam proses pengembangan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.getSession().observe(this) { user ->

            val firstLetter = user.name.firstOrNull()?.toString() ?: ""


            binding.apply {
                profileIcon.text = firstLetter.uppercase()
            }
        }
    }
}