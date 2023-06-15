package com.c23ps160.nutriscan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()

        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("name")

        findViewById<TextView>(R.id.tv_disp_name).text = displayName
        findViewById<TextView>(R.id.tv_disp_mail).text = email


        val backIcon: ImageView = findViewById(R.id.backicon)
        backIcon.setOnClickListener {
            finish()
        }
//        val button2: Button = findViewById(R.id.button2)
//        button2.setOnClickListener {
//            Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
//        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, ContinueActivity::class.java))
            finish()
        }
    }
}