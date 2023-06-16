package com.c23ps160.nutriscan

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.c23ps160.nutriscan.Model.QuickFood
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val backIcon: ImageView = findViewById(R.id.backicon)
        val imageView: ImageView = findViewById(R.id.ivFoodImg)
        val tvFoodName: TextView = findViewById(R.id.tvFoodName)
        val tvUkuranSaji = findViewById<TextView>(R.id.tvUkuranSaji)
        val tvKalori = findViewById<TextView>(R.id.tvKalori)
        val tvKarbohidrat = findViewById<TextView>(R.id.tvKarbohidrat)
        val tvLemak = findViewById<TextView>(R.id.tvLemak)
        val tvProtein = findViewById<TextView>(R.id.tvProtein)

        backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        var foodName: String? = null
        var foodClass: String? = null

        // Catch data from previous Activity
        val resultType = intent.getStringExtra("Type")
        if(resultType == "Scan") {
            val foodImg: Bitmap? = MainActivity.foodData.image
            foodName = MainActivity.foodData.foodName
            foodClass = MainActivity.foodData.foodClass

            imageView.setImageBitmap(foodImg)
        } else {
            val food: QuickFood? = intent.getSerializableExtra("Food") as? QuickFood
            foodClass = food?.foodClass
            foodName = food?.foodNames
            if (food != null) {
                imageView.setImageResource(food.foodImage)
            }
            tvFoodName.text = foodName
        }

        tvFoodName.text = foodName

        if (foodClass != null) {
            FirebaseApp.initializeApp(this)
            val db: DatabaseReference = FirebaseDatabase.getInstance("https://c23-ps106.firebaseio.com/").getReference("Foods")
//            val db: DatabaseReference = Firebase.database.getReference("Foods")
            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val ukuranSaji =
                        snapshot.child(foodClass).child("UkuranSaji").getValue<String>()
                    val kalori = snapshot.child(foodClass).child("Kalori").getValue<String>()
                    val karbohidrat =
                        snapshot.child(foodClass).child("Karbohidrat").getValue<String>()
                    val lemak = snapshot.child(foodClass).child("Lemak").getValue<String>()
                    val protein = snapshot.child(foodClass).child("Protein").getValue<String>()

                    tvUkuranSaji.text = ukuranSaji
                    tvKalori.text = kalori
                    tvKarbohidrat.text = karbohidrat
                    tvLemak.text = lemak
                    tvProtein.text = protein
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
}