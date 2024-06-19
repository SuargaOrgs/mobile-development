package com.dicoding.suargaapp.ui.riwayat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.data.remote.response.ErrorResponse
import com.dicoding.suargaapp.data.remote.response.HistoryItem
import com.dicoding.suargaapp.databinding.FragmentRiwayatBinding
import com.dicoding.suargaapp.ui.main.MainViewModel
import com.dicoding.suargaapp.ui.profile.ProfileFragment
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RiwayatFragment : Fragment() {

    private lateinit var binding: FragmentRiwayatBinding

    private val riwayatViewModel by viewModels<RiwayatViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }

    private val mainViewModel by viewModels<MainViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        loadFoodHistory()
        observeViewModel()
    }

    private fun setupAction(){
        binding.avatar.setOnClickListener {
            val profileFragment = ProfileFragment()

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, profileFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun loadFoodHistory() {
        showLoading(true)
        lifecycleScope.launch {
            try {
                val foodHistory = riwayatViewModel.historyFood()
                showRecyclerView(foodHistory.data)
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

    private fun showRecyclerView(item : List<HistoryItem>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = FoodHistoryAdapter()
        binding.recyclerView.adapter = adapter
        adapter.submitList(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun observeViewModel() {
        mainViewModel.getSession().observe(viewLifecycleOwner) { user ->
            val greetings = "Hallo Ibu, ${user.name}!"
            val firstLetter = user.name.firstOrNull()?.toString() ?: ""

            binding.apply {
                tvGreeting.text = greetings
                avatar.text = firstLetter.uppercase()
            }
        }
    }
}
