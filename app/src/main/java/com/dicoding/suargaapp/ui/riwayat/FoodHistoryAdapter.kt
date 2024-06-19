package com.dicoding.suargaapp.ui.riwayat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.suargaapp.data.remote.response.HistoryItem
import com.dicoding.suargaapp.databinding.ItemFoodHistoryBinding

class FoodHistoryAdapter : ListAdapter<HistoryItem, FoodHistoryAdapter.FoodHistoryViewHolder>(DIFF_CALLBACK) {

    class FoodHistoryViewHolder(private val binding: ItemFoodHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryItem) {
            Glide.with(binding.root)
                .load(item.gambar)
                .into(binding.ivFoodIcon)
            binding.tvFoodName.text = item.namaMakanan
            binding.tvDateFood.text = item.keterangan
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHistoryViewHolder {
        val binding = ItemFoodHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodHistoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryItem>() {
            override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}
