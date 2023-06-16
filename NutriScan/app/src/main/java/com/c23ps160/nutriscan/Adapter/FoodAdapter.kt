package com.c23ps160.nutriscan.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.c23ps160.nutriscan.Model.QuickFood
import com.c23ps160.nutriscan.R

class FoodAdapter(private val foodList : List<QuickFood>) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val foodImageView : ImageView = itemView.findViewById( R.id.mealImage)
        val foodNameTextView : TextView = itemView.findViewById( R.id.mealName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quick_acc , parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.foodImageView.setImageResource(food.foodImage)
        holder.foodNameTextView.text = food.foodNames
    }
}