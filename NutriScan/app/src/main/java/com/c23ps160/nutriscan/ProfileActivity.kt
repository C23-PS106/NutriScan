package com.c23ps160.nutriscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val backIcon: ImageView = findViewById(R.id.backicon)
        backIcon.setOnClickListener {
            finish()
        }
    }
}