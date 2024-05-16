package com.dicoding.suargaapp.ui.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityOnBoardingPageEndBinding
import com.dicoding.suargaapp.databinding.ActivityOnBoardingPageMidBinding
import com.dicoding.suargaapp.ui.login.LoginActivity
import com.dicoding.suargaapp.ui.signup.SignupActivity

class OnBoardingPageEnd : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingPageEndBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingPageEndBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {

        binding.myButton.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
        }

        val textView = binding.tvLoginLink
        val text = "Sudah punya akun? Masuk"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@OnBoardingPageEnd, LoginActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = ContextCompat.getColor(this@OnBoardingPageEnd, R.color.primary60)
            }
        }

        val start = text.indexOf("Masuk")
        val end = start + "Masuk".length
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.primary60)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}