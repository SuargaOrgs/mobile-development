package com.dicoding.suargaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.suargaapp.R
import com.dicoding.suargaapp.adapter.ArticleAdapter.Companion.DIFF_CALLBACK
import com.dicoding.suargaapp.data.local.Article
import com.dicoding.suargaapp.data.remote.response.Food
import com.dicoding.suargaapp.data.remote.response.Nutrition
import com.dicoding.suargaapp.databinding.ItemFoodBinding

class FoodAdapter(
    private val foodList: MutableList<Food>,
    private val onEditClick: (Food) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFoodName: TextView = itemView.findViewById(R.id.tv_food_name)
        val ivEdit: ImageButton = itemView.findViewById(R.id.iv_edit)
        val ivDelete: ImageButton = itemView.findViewById(R.id.iv_delete)
        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Fitur masih dalam proses pengembangan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.tvFoodName.text = food.namaMakanan
        holder.ivEdit.setOnClickListener { onEditClick(food) }
        holder.ivDelete.setOnClickListener { onDeleteClick(position) }
    }

    override fun getItemCount(): Int = foodList.size
}
