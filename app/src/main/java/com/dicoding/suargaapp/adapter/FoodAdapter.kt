package com.dicoding.suargaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.suargaapp.R

class FoodAdapter(private val foodList: MutableList<String>, private val onEditClick: (Int) -> Unit, private val onDeleteClick: (Int) -> Unit) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodName = foodList[position]
        holder.tvFoodName.text = foodName
        holder.ivEdit.setOnClickListener { onEditClick(position) }
        holder.ivDelete.setOnClickListener { onDeleteClick(position) }
    }

    override fun getItemCount(): Int = foodList.size

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFoodName: TextView = itemView.findViewById(R.id.tv_food_name)
        val ivEdit: ImageButton = itemView.findViewById(R.id.iv_edit)
        val ivDelete: ImageButton = itemView.findViewById(R.id.iv_delete)
    }
}
