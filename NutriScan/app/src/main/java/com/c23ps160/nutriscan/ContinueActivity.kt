package com.c23ps160.nutriscan

import android.content.ContentProviderClient
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth

class ContinueActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue)

        auth = FirebaseAuth.getInstance()

        val signInButton: SignInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener {
            val toMainActivity = Intent(this, MainActivity::class.java)
            startActivity(toMainActivity)
        }
    }
}