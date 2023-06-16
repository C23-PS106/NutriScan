package com.c23ps160.nutriscan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.c23ps160.nutriscan.Login.LoginActivity
import com.c23ps160.nutriscan.Login.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()

        val sessionManager = SessionManager(applicationContext)

        val displayName = sessionManager.getName()
        val email = sessionManager.getEmail()

        findViewById<TextView>(R.id.tvUkuranSaji).text = displayName
        findViewById<TextView>(R.id.tv_disp_mail).text = email


        val backIcon: ImageView = findViewById(R.id.backicon)
        backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
//        val button2: Button = findViewById(R.id.button2)
//        button2.setOnClickListener {
//            Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
//        }

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
//            auth.signOut()
//            val sessionManager = SessionManager(applicationContext)
//            sessionManager.setName("")
//            sessionManager.setEmail("")
//            sessionManager.setLogin(false)
//
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
            signOut()
        }
    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val gsc = GoogleSignIn.getClient(this, gso)
        FirebaseAuth.getInstance().signOut()
        gsc.signOut().addOnCompleteListener {
            val sessionManager = SessionManager(applicationContext)
            sessionManager.setName("")
            sessionManager.setEmail("")
            sessionManager.setLogin(false)

            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        super.onBackPressed()
    }
}