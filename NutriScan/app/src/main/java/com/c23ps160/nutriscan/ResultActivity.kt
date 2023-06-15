package com.c23ps160.nutriscan

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Catch data from previous Activity
        val foodImg: Bitmap? = MainActivity.foodData.image
        val foodName: String? = MainActivity.foodData.foodName

        val imageView: ImageView = findViewById(R.id.ivFoodImg)
        val tvFoodClass: TextView = findViewById(R.id.tvFoodName)
        val textViewUkuranSaji = findViewById<TextView>(R.id.tvUkuranSaji)
        val textViewKalori = findViewById<TextView>(R.id.tvKalori)
        val textViewKarbohidrat = findViewById<TextView>(R.id.tvKarbohidrat)
        val textViewLemak = findViewById<TextView>(R.id.tvLemak)
        val textViewProtein = findViewById<TextView>(R.id.tvProtein)

        imageView.setImageBitmap(foodImg)
        tvFoodClass.text = foodName
    }
}