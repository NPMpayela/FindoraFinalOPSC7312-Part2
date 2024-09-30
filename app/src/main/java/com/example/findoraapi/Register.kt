package com.example.findoraapi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.findoraapi.Models.User
import com.example.findoraapi.Models.MyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Register : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var btnRegister: Button
    private lateinit var loginPg: Button

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.btnRegister)
        val loginPg = findViewById<Button>(R.id.loginPg)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://workingnodefandoraapi-production.up.railway.app")
            // alternative link using Render : https://findoraandriodapp-xyft.onrender.com")  // Add your backend URL here
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(username, password)
            registerUser(user)
        }

        loginPg.setOnClickListener{
            LoginPage()
        }
    }

    private fun registerUser(user: User) {
        apiService.registerUser(user).enqueue(object : Callback<MyResponse> {
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Register, response.body()?.message, Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@Register, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                Toast.makeText(this@Register, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun LoginPage(){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
}