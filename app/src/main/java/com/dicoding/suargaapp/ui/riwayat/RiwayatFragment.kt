package com.dicoding.suargaapp.ui.riwayat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.data.local.history.FoodHistoryItem

class RiwayatFragment : Fragment() {

    private lateinit var foodHistoryAdapter: FoodHistoryAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_riwayat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}
