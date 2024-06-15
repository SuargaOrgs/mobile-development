package com.dicoding.suargaapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.data.remote.response.DataItem
import com.dicoding.suargaapp.data.remote.response.ErrorResponse
import com.dicoding.suargaapp.databinding.FragmentHomeBinding
import com.dicoding.suargaapp.helper.Helper.calculatePregnancyAge
import com.dicoding.suargaapp.helper.Helper.getCurrentDate
import com.dicoding.suargaapp.ui.asesmen.AsesmenActivity
import com.dicoding.suargaapp.ui.login.LoginActivity
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class HomeFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        observeViewModel()
        binding.tvCalendar.text = getCurrentDate()
    }

    private fun setupAction() {

        lifecycleScope.launch {
            try {
                showLoading(true)
                val response = homeViewModel.getAssessmentResult()
                val data = response.data

                if (data != null) {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

                    val sortedData = data.filterNotNull().sortedWith(compareByDescending<DataItem> { item ->
                        item.updatedAt?.let { dateFormat.parse(it) } ?: Date(0)
                    }.thenByDescending { item ->
                        item.idAssessment
                    })

                    val recentItem = sortedData.firstOrNull()

                    recentItem?.let {
                        binding.apply {
                            tvKarbohidatMax.text = getString(R.string.karbohidrat_max, it.karbohidrat)
                            tvProteinMax.text = getString(R.string.protein_max, it.protein)
                            tvLemakMax.text = getString(R.string.lemak_max, it.lemak)
                        }
                    }
                }
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    Toast.makeText(requireContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show()
                    redirectToLogin()
                } else {
                    val jsonInString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody.message
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), AsesmenActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

        lifecycleScope.launch {
            try {
                showLoading(true)
                val response = homeViewModel.listNutrition()
                val data = response.data

                if (data != null) {
                    val currentDateUTC = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }.format(Date())
                    val nutrition = data.filterNotNull().filter {
                        it.createdAt?.startsWith(currentDateUTC) == true
                    }.fold(TotalNutrients()) { acc, item ->
                        acc.apply {
                            val porsi = item.porsi ?: 1
                            karbohidrat += (item.karbohidrat ?: 0.0) * porsi
                            lemak += (item.lemak ?: 0.0) * porsi
                            protein += (item.protein ?: 0.0) * porsi
                        }
                    }

                    binding.apply {
                        tvKarbohidratMin.text = getString(R.string.karbohidrat_min, (nutrition.karbohidrat.toInt()))
                        tvProteinMin.text = getString(R.string.protein_min, (nutrition.protein.toInt()))
                        tvLemakMin.text = getString(R.string.lemak_min, (nutrition.lemak.toInt()))
                    }
                }
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            } finally {
                showLoading(false)
            }
        }
    }

    private fun observeViewModel() {
        homeViewModel.getSession().observe(viewLifecycleOwner) { user ->
            val greetings = "Hallo Ibu, ${user.name}!"
            val pregnancyAge= calculatePregnancyAge(user.pregnancyDate)
            val expectedBirth = 40 - pregnancyAge

            binding.apply {
                tvGreeting.text = greetings
                tvPregnancyAge.text = pregnancyAge.toString()
                tvExpectedBirth.text = expectedBirth.toString()
            }
        }
    }

    private fun redirectToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

data class TotalNutrients(
    var karbohidrat: Double = 0.0,
    var lemak: Double = 0.0,
    var protein: Double = 0.0
)
