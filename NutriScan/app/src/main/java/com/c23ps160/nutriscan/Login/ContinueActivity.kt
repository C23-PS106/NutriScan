package com.c23ps160.nutriscan.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.c23ps160.nutriscan.MainActivity
import com.c23ps160.nutriscan.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class ContinueActivity : AppCompatActivity() {

    /*private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue)

//        val sessionManager = SessionManager(applicationContext)
//        if (sessionManager.getLogin()) {
//            startActivity(Intent(this@ContinueActivity, MainActivity::class.java))
//            finish()
//        }


        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener {
            signInGoogle()
        }

//        val signInButton: SignInButton = findViewById(R.id.sign_in_button)
//        signInButton.setOnClickListener {
//            val toMainActivity = Intent(this, MainActivity::class.java)
//            startActivity(toMainActivity)
//        }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            } else {
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                if (auth.currentUser!= null) {
//                    val sessionManager = SessionManager(applicationContext)
//                    sessionManager.setLogin(true)

                    val intent: Intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("email", account.email)
                    intent.putExtra("name", account.displayName)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    private val SIGN_IN_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue)

        val sessionManager = SessionManager(applicationContext)
        if (sessionManager.getLogin()) {
            startActivity(Intent(this@ContinueActivity, MainActivity::class.java))
            finish()
        }

        val signInButton: SignInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener { signIn() }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val gsc = GoogleSignIn.getClient(this, gso)
        val intent = gsc.signInIntent
        startActivityForResult(intent, SIGN_IN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val mAuth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@ContinueActivity, "Login succesful", Toast.LENGTH_SHORT).show()
                    val name = task.result.user!!.displayName
                    val email = task.result.user!!.email

                    val sessionManager = SessionManager(applicationContext)
                    sessionManager.setLogin(true)
                    sessionManager.setName(name)
                    sessionManager.setEmail(email)

                    startActivity(Intent(this@ContinueActivity, MainActivity::class.java))
                    finish()
                }
            }
    }
}