package com.dicoding.suargaapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.databinding.ActivityMainBinding
import com.dicoding.suargaapp.ui.article.ArticleFragment
import com.dicoding.suargaapp.ui.camera.CameraActivity
import com.dicoding.suargaapp.ui.home.HomeFragment
import com.dicoding.suargaapp.ui.onboarding.OnBoardingPageStart
import com.dicoding.suargaapp.ui.profile.ProfileFragment
import com.dicoding.suargaapp.ui.riwayat.RiwayatFragment
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private val mainViewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.background = null


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.botton_home -> openFragment(HomeFragment())
                R.id.botton_artikel -> openFragment(ArticleFragment())
                R.id.botton_riwayat -> openFragment(RiwayatFragment())
                R.id.botton_profile -> openFragment(ProfileFragment())
            }
            true
        }


        binding.fabAddpic.setOnClickListener{
            startActivity(Intent(this, CameraActivity::class.java))
        }

        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnBoardingPageStart::class.java))
                finish()
            } else {
                fragmentManager = supportFragmentManager
                openFragment(HomeFragment())
            }
        }

    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}