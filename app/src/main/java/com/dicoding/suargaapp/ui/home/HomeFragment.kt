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
import com.dicoding.suargaapp.helper.Helper.convertUTCToWIB
import com.dicoding.suargaapp.helper.Helper.getCurrentDate
import com.dicoding.suargaapp.ui.article.ArticleFragment
import com.dicoding.suargaapp.ui.asesmen.AsesmenActivity
import com.dicoding.suargaapp.ui.login.LoginActivity
import com.dicoding.suargaapp.ui.premium.PremiumActivity
import com.dicoding.suargaapp.ui.profile.ProfileFragment
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.round

class HomeFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }
    private lateinit var binding: FragmentHomeBinding
    private var karbohidratMax: Int? = 0
    private var proteinMax: Int? = 0
    private var lemakMax: Int? = 0

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
                try {
                    val assessmentResponse = homeViewModel.getAssessmentResult()
                    val assessmentData = assessmentResponse.data

                    if (assessmentData != null) {
                        val dateFormat =
                            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

                        val sortedData = assessmentData.filterNotNull()
                            .sortedWith(compareByDescending<DataItem> { item ->
                                item.updatedAt?.let { dateFormat.parse(it) } ?: Date(0)
                            }.thenByDescending { item ->
                                item.idAssessment
                            })

                        val recentItem = sortedData.firstOrNull()

                        recentItem?.let {
                            karbohidratMax = it.karbohidrat
                            proteinMax = it.protein
                            lemakMax = it.lemak
                            binding.apply {
                                tvKarbohidatMax.text = getString(R.string.karbohidrat_max, karbohidratMax)
                                tvProteinMax.text = getString(R.string.protein_max, proteinMax)
                                tvLemakMax.text = getString(R.string.lemak_max, lemakMax)
                            }
                        }
                    }
                } catch (e: HttpException) {
                    if (e.code() == 401) {
                        Toast.makeText(
                            requireContext(),
                            "Session expired. Please login again.",
                            Toast.LENGTH_SHORT
                        ).show()
                        redirectToLogin()
                        return@launch
                    } else {
                        val jsonInString = e.response()?.errorBody()?.string()
                        val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                        val errorMessage = errorBody.message
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), AsesmenActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                        return@launch
                    }
                }

                try {
                    val nutritionResponse = homeViewModel.listNutrition()
                    val nutritionData = nutritionResponse.data

                    if (nutritionData != null) {
                        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
                            timeZone = TimeZone.getTimeZone("Asia/Jakarta")
                        }.format(Date())
                        val nutrition = nutritionData.filterNotNull().filter {
                            val createdAt = it.createdAt
                            Log.d("cek created at", "setupAction: $createdAt ")
                            val createdAtWIB = createdAt?.let { it1 -> convertUTCToWIB(it1) }
                            Log.d("cek created at WIB", "setupAction: $createdAtWIB ")
                            createdAtWIB == currentDate
                        }.fold(TotalNutrients()) { acc, item ->
                            acc.apply {
                                val porsi = item.porsi ?: 1
                                karbohidrat += (item.karbohidrat ?: 0.0) * porsi
                                lemak += (item.lemak ?: 0.0) * porsi
                                protein += (item.protein ?: 0.0) * porsi
                            }
                        }

                        binding.apply {
                            tvKarbohidratMin.text = getString(R.string.karbohidrat_min, round(nutrition.karbohidrat).toInt())
                            tvProteinMin.text = getString(R.string.protein_min, round(nutrition.protein).toInt())
                            tvLemakMin.text = getString(R.string.lemak_min, round(nutrition.lemak).toInt())
                        }

                        karbohidratMax?.let {
                            updateProgressBar(nutrition.karbohidrat, it, "karbohidrat")
                        }

                        proteinMax?.let {
                            updateProgressBar(nutrition.protein, it, "protein")
                        }

                        lemakMax?.let {
                            updateProgressBar(nutrition.lemak, it, "lemak")
                        }
                    }
                } catch (e: HttpException) {
                    Log.e("NutritionFetchError", "Error fetching nutrition data: ${e.message()}")
                }
            } finally {
                showLoading(false)
            }
        }
        binding.button.setOnClickListener {
            val articleFragment = ArticleFragment()

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, articleFragment)
                addToBackStack(null)
                commit()
            }
        }
        binding.avatar.setOnClickListener {
            val profileFragment = ProfileFragment()

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, profileFragment)
                addToBackStack(null)
                commit()
            }
        }
        binding.learnButton.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur masih dalam proses pengembangan", Toast.LENGTH_SHORT).show()
        }
    }


    private fun observeViewModel() {
        homeViewModel.getSession().observe(viewLifecycleOwner) { user ->
            val greetings = "Hallo Ibu, ${user.name}!"
            val firstLetter = user.name.firstOrNull()?.toString() ?: ""
            val pregnancyAge= calculatePregnancyAge(user.pregnancyDate)
            val expectedBirth = 40 - pregnancyAge

            binding.apply {
                tvGreeting.text = greetings
                avatar.text = firstLetter.uppercase()
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

    private fun updateProgressBar(valueMin: Double, valueMax: Int, type: String) {
        val percentage = if (valueMax != 0) {
            (valueMin / valueMax * 100).toInt()
        } else {
            0
        }

        when (type) {
            "karbohidrat" -> binding.karbohidratBar.progress = percentage
            "protein" -> binding.proteinBar.progress = percentage
            "lemak" -> binding.lemakBar.progress = percentage
        }
    }
}

data class TotalNutrients(
    var karbohidrat: Double = 0.0,
    var lemak: Double = 0.0,
    var protein: Double = 0.0
)
