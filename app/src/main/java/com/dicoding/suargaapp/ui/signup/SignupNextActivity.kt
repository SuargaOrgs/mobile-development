package com.dicoding.suargaapp.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivitySignupBinding
import com.dicoding.suargaapp.databinding.ActivitySignupNextBinding
import com.dicoding.suargaapp.ui.login.LoginActivity
import com.dicoding.suargaapp.viewmodelfactory.ViewModelFactory

class SignupNextActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupNextBinding
    private val signUpViewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupNextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

        binding.nameEditText.setParentLayout(binding.nameEditTextLayout)
    }

    private fun setupAction() {
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        Log.d("cekEmail", "email: $email, password: $password")

        binding.myButton.setOnClickListener {
            // Check if email and password are not null
            if (email != null && password != null) {
                val name = binding.nameEditText.text.toString()

                signUpViewModel.register(name, email, password).observe(this) { result ->
                    if (result.error == false) {
                        AlertDialog.Builder(this).apply {
                            setTitle("Success")
                            setMessage(result.message)
                            setPositiveButton("Next") { _, _ ->
                                val intent = Intent(this@SignupNextActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Failed")
                            setMessage(result.message)
                            setPositiveButton("Try Again") { _, _ ->
                                val intent = Intent(this@SignupNextActivity, SignupActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                }
            } else {
                Log.e("SignupNextActivity", "Email or password is null")
            }
        }
    }
}