package com.example.findoraapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.findoraapi.Models.User
import com.example.findoraapi.Models.MyResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.android.gms.tasks.Task

class Login : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var btnLogin: Button
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var apiService: ApiService

    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(3000)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        btnLogin = findViewById(R.id.btnLogin)
        val registerButton = findViewById<Button>(R.id.btnRegister)
        val googleSignInBtn = findViewById<Button>(R.id.googleSignInButton)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://workingnodefandoraapi-production.up.railway.app")
        // alternative link using Render : https://findoraandriodapp-xyft.onrender.com") // Your backend URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btnLogin.setOnClickListener {
            val username = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(username, password)
            loginUser(user)
        } // Login using credentials

        googleSignInBtn.setOnClickListener {
            signInWithGoogle()
        } // Login using Google

        registerButton.setOnClickListener {
            RegisterPage()
        }
    }

    //User api call to login users
    private fun loginUser(user: User) {
        apiService.loginUser(user).enqueue(object : Callback<MyResponse> {
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Login, response.body()?.message ?: "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Login, HomePage::class.java)
                    startActivity(intent)
                } else {
                    // Log the error message for debugging
                    Log.d("Login", "Login failed with error: ${response.errorBody()?.string()}")

                    // Show a Toast with the error message
                    Toast.makeText(this@Login, "Login failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                // Handle failure here
                Log.e("Login", "Error: ${t.message}", t)
                Toast.makeText(this@Login, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    // Checks login details using the API

    private fun signInWithGoogle() {
        // Sign out the user to show the account chooser every time
        googleSignInClient.signOut().addOnCompleteListener {
            // After signing out, launch the sign-in intent
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Google sign in successful", Toast.LENGTH_SHORT).show()
                    // Navigate to main activity
                    startActivity(Intent(this, HomePage::class.java)) // change to desired page
                    finish()
                } else {
                    Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun RegisterPage() {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }
}
