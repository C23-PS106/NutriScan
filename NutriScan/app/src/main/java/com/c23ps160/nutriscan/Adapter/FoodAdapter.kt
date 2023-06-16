package com.c23ps160.nutriscan.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.c23ps160.nutriscan.Model.QuickFood
import com.c23ps160.nutriscan.R
import com.c23ps160.nutriscan.ResultActivity

class FoodAdapter(private val foodList : List<QuickFood>, private val context: Context) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val foodImageView : ImageView = itemView.findViewById( R.id.mealImage)
        val foodNameTextView : TextView = itemView.findViewById( R.id.mealName)
        val qaItem : CardView = itemView.findViewById( R.id.qaItem)
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
        holder.qaItem.setOnClickListener{
            val intent = Intent(context, ResultActivity::class.java)
            intent.putExtra("Type", "QuickAccess")
            intent.putExtra("Food", food)
            context.startActivity(intent)
        }
    }
}