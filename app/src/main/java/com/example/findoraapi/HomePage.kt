package com.example.findoraapi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
 import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:4659" // Use this IP address for emulator

    val api: EventService by lazy {
        Retrofit.Builder()
           // .baseUrl("https://findoraandriodapp-xyft.onrender.com") // Your backend URL
            .baseUrl("https://workingnodefandoraapi-production.up.railway.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventService::class.java)
    }
}



class HomePage : AppCompatActivity() {

    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var eventAdapter: FindoraEventAdapter
    private var events: List<Event> = listOf()



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_page)

        val settingsButton: ImageButton = findViewById(R.id.settings_button)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Initialize RecyclerView
        eventRecyclerView = findViewById(R.id.eventRecyclerView)

        // Set Layout Manager for RecyclerView (LinearLayoutManager for a vertical list)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch events (from Retrofit or any other data source)
        fetchEvents()

        // Set up adapter with event list (once events are available)
        eventAdapter = FindoraEventAdapter(events)
        eventRecyclerView.adapter = eventAdapter

        val spinner2 = findViewById<Spinner>(R.id.eventSpinner)
        val adapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.eventfilter,
            R.layout.simple_spinner_item // Custom item layout
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2



        findViewById<Button>(R.id.btnHostnext).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.mapsbtn).setOnClickListener(){
            val intent = Intent(this, GoogleMapPage::class.java)
            startActivity(intent)
        }


        // Set up the RecyclerView
        eventRecyclerView = findViewById(R.id.eventRecyclerView)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)



    }

    private fun fetchEvents() {
        RetrofitInstance.api.getEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    // Update events and notify the adapter
                    val events = response.body()?.events ?: emptyList()
                    eventRecyclerView.adapter = FindoraEventAdapter(events)
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                // Handle failure, e.g., show a Toast
            }
        })
    }



}