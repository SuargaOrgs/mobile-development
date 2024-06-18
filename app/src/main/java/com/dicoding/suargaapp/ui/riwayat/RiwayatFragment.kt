package com.dicoding.suargaapp.ui.riwayat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.data.local.history.FoodHistoryItem
import com.dicoding.suargaapp.databinding.FragmentRiwayatBinding
import com.dicoding.suargaapp.ui.main.MainViewModel
import com.dicoding.suargaapp.viewmodelfactory.AuthViewModelFactory

class RiwayatFragment : Fragment() {

    private lateinit var binding: FragmentRiwayatBinding
    private lateinit var foodHistoryAdapter: FoodHistoryAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel by viewModels<MainViewModel> {
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

        observeViewModel()

        recyclerView = view.findViewById(R.id.recyclerView)

        foodHistoryAdapter = FoodHistoryAdapter(getFoodHistory())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = foodHistoryAdapter
    }

    private fun getFoodHistory(): List<FoodHistoryItem> {
        return listOf(
            FoodHistoryItem("Nasi Goreng", "Pagi | 31 May 2024"),
            FoodHistoryItem("Nasi Kandar", "Siang | 31 May 2024"),
            FoodHistoryItem("Nasi Ayam", "Malam | 31 May 2024"),
            FoodHistoryItem("Pisang", "Pagi | 1 Juni 2024"),
        )
    }

    private fun observeViewModel() {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            val greetings = "Hallo Ibu, ${user.name}!"

            binding.apply {
                tvGreeting.text = greetings
            }
        }
    }
}
