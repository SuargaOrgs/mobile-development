package com.dicoding.suargaapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityLoginBinding
import com.dicoding.suargaapp.ui.main.MainActivity
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

        val emailEditText = binding.emailEditText
        val passwordEditText = binding.passwordEditText

        emailEditText.setParentLayout(binding.emailEditTextLayout)
        passwordEditText.setParentLayout(binding.passwordEditTextLayout)
    }

    private fun setupAction(){

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            loginViewModel.login(email, password)

            loginViewModel.loginResult.observe(this) { loginResult ->
                when (loginResult) {
                    is LoginViewModel.LoginResult.Success -> {
                        loginResult.user

                        AlertDialog.Builder(this)
                            .setTitle("Login Success")
                            .setMessage("Ayo buruan masuk")
                            .setPositiveButton(getString(R.string.next)) { _, _ ->
                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            .create()
                            .show()
                    }

                    is LoginViewModel.LoginResult.Error -> {
                        val errorResponse = loginResult.errorResponse
                        val errorMessage = errorResponse.message

                        AlertDialog.Builder(this)
                            .setTitle("Login Failed")
                            .setMessage("Error: $errorMessage" )
                            .setPositiveButton(getString(R.string.next)) { _, _ ->
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .create()
                            .show()
                    }
                }
            }

            loginViewModel.isLoading.observe(this) {
                showLoading(it)
            }
        }

        val textView = binding.tvForgotPass
        val text = "Lupa Password?"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@LoginActivity, "Halaman ganti password", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = ContextCompat.getColor(this@LoginActivity, R.color.primary60)
            }
        }

        val start = text.indexOf("Lupa Password?")
        val end = start + "Lupa Password?".length
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.primary80)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}