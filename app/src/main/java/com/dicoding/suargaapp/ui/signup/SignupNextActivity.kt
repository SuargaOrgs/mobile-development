package com.dicoding.suargaapp.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.suargaapp.databinding.ActivitySignupNextBinding
import com.dicoding.suargaapp.ui.asesmen.AsesmenActivity
import com.dicoding.suargaapp.ui.login.LoginActivity
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory

class SignupNextActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupNextBinding
    private val signUpViewModel by viewModels<SignUpViewModel> {
        AuthViewModelFactory.getInstance(this)
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
            if (email != null && password != null) {
                val fullName = binding.nameEditText.text.toString()
                val birthday = binding.dateMotherEditText.text.toString()
                val pregnancyDate = binding.dateBabyEditText.text.toString()

                Log.d("cekNama", "name: $fullName, birthdate: $birthday, pregnancy: $pregnancyDate")

                signUpViewModel.register(email, password, fullName, birthday, pregnancyDate).observe(this) { result ->
                    if (result.error == false) {

                        AlertDialog.Builder(this).apply {
                            setTitle("Success")
                            setMessage(result.message)
                            setPositiveButton("Next") { _, _ ->
                                val intent = Intent(this@SignupNextActivity, AsesmenActivity::class.java)
                                intent.putExtra("birthday", birthday)
                                intent.putExtra("pregnancyDate", pregnancyDate)
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

        signUpViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}