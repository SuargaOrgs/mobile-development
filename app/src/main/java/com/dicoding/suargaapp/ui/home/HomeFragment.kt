package com.dicoding.suargaapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.data.remote.response.ErrorResponse
import com.dicoding.suargaapp.databinding.FragmentHomeBinding
import com.dicoding.suargaapp.helper.Helper.calculatePregnancyAge
import com.dicoding.suargaapp.helper.Helper.getCurrentDate
import com.dicoding.suargaapp.ui.asesmen.AsesmenActivity
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

                    val sortedData = data.filterNotNull().sortedByDescending { item ->
                        item.updatedAt?.let { dateFormat.parse(it) } ?: Date(0)
                    }

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
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), AsesmenActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
