package com.dicoding.suargaapp.ui.detailprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dicoding.suargaapp.databinding.ActivityDetailProfileBinding
import com.dicoding.suargaapp.ui.editprofile.EditProfileActivity
import com.dicoding.suargaapp.ui.main.MainActivity
import com.dicoding.suargaapp.ui.main.MainViewModel
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

class DetailProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProfileBinding
    private val viewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupAction()
    }

    private fun observeViewModel() {
        viewModel.getSession().observe(this) { user ->

            val firstLetter = user.name.firstOrNull()?.toString() ?: ""

            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

            val formattedDate = try {
                val date = inputDateFormat.parse(user.pregnancyDate)
                outputDateFormat.format(date)
            } catch (e: Exception) {
                user.pregnancyDate
            }

            binding.apply {
                userName.text = user.name
                userEmail.text = user.email
                birthDate.text = formattedDate
                profileIcon.text = firstLetter.uppercase()
            }
        }
    }
    private fun setupAction() {

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.buttonEdit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }
}