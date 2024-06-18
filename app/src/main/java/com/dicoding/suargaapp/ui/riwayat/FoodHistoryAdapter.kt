package com.dicoding.suargaapp.ui.riwayat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.data.local.history.FoodHistoryItem

class FoodHistoryAdapter(private val foodHistoryList: List<FoodHistoryItem>) :
    RecyclerView.Adapter<FoodHistoryAdapter.FoodHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_history, parent, false)
        return FoodHistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodHistoryViewHolder, position: Int) {
        val currentItem = foodHistoryList[position]
        holder.foodNameTextView.text = currentItem.name
        holder.foodDateTextView.text = currentItem.date
    }

    override fun getItemCount() = foodHistoryList.size

    class FoodHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodIconImageView: ImageView = itemView.findViewById(R.id.iv_food_icon)
        val foodNameTextView: TextView = itemView.findViewById(R.id.tv_food_name)
        val foodDateTextView: TextView = itemView.findViewById(R.id.tv_date_food)
        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Fitur masih dalam proses pengembangan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
