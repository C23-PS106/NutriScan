package com.c23ps160.nutriscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val backIcon: ImageView = findViewById(R.id.backicon)
        backIcon.setOnClickListener {
            finish()
        }
        val button2: Button = findViewById(R.id.button2)
        button2.setOnClickListener {
            Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
        }
    }
}