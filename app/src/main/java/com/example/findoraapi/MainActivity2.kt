package com.example.findoraapi

//import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
//import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineStart
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import kotlin.io.encoding.Base64

private lateinit var eventapiservice: EventService

class MainActivity2 : AppCompatActivity() {

    private lateinit var event: Event


    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    private val PICK_IMAGE_REQUEST = 1
    private val eventList = arrayListOf<Event>()

    private lateinit var imageView: ImageView
    private lateinit var startTimeTP: EditText//TimePicker
    private lateinit var endTimeTP: EditText //TimePicker
    private lateinit var eventType: Spinner


    // @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://workingnodefandoraapi-production.up.railway.app")
            //  .client(getUnsafeOkHttpClient())// Add your backend URL here
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        eventapiservice = retrofit.create(EventService::class.java)
//Vasava, K. (2023) Retrofit in Android, Medium. Medium. Available at: https://medium.com/@KaushalVasava/retrofit-in-android-5a28c8e988ce (Accessed: 25 September 2024).

        imageView = findViewById(R.id.imageView)
// Set up click listeners for the buttons


        // Set up spinner
        val spinner = findViewById<Spinner>(R.id.eventType)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.eventType,
            R.layout.simple_spinner_item // Custom item layout
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //   event = intent.getParcelableExtra("EVENT_DATA") ?: Event()
        //   event = intent.getParcelableExtra("EVENT_DATA") ?: Event()
        // Get the buttons and text views
        val startTimeButton: Button = findViewById(R.id.start_time_button)
        val endTimeButton: Button = findViewById(R.id.end_time_button)
        val startTimeText: TextView = findViewById(R.id.start_time_text)
        val endTimeText: TextView = findViewById(R.id.end_time_text)
        eventType = spinner

        startTimeButton.setOnClickListener {
            showTimePicker { time ->
                startTimeText.text = "Start Time: $time"
            }
        }

        endTimeButton.setOnClickListener {
            showTimePicker { time ->
                endTimeText.text = "End Time: $time"
            }
        }

            findViewById<Button>(R.id.button).setOnClickListener {
                openImagePicker()
            }

            findViewById<Button>(R.id.btnBack).setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            findViewById<Button>(R.id.btnUploadevent).setOnClickListener {


                val event = intent.getParcelableExtra<Event>("event")

                // Make the API call to create the event
                if (event != null) {
                    eventapiservice.createEvent(event).enqueue(object : retrofit2.Callback<Void> {
                        override fun onResponse(
                            call: Call<Void>,
                            response: retrofit2.Response<Void>
                        ) {
                            if (response.isSuccessful) {
                                // Event created successfully
                                Toast.makeText(
                                    this@MainActivity2,
                                    "Event created successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Proceed to the next activity (MainActivity2)
                                val intent = Intent(this@MainActivity2, HomePage::class.java)
                                startActivity(intent)
                            } else {
                                // Handle the API error response
                                val errorBody = response.errorBody()?.string()
                                Log.e(
                                    "API Error",
                                    "Response code: ${response.code()} - Message: $errorBody"
                                )
                                Toast.makeText(
                                    this@MainActivity2,
                                    "Failed to create event. Error: ${response.code()}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("Event Data", event.toString())
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            // Handle the failure in calling the API
                            Log.e("API Error", "Failed to call API: ${t.message}")
                            Toast.makeText(
                                this@MainActivity2,
                                "API call failed",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("Event Data", event.toString())

                        }
                    })
                }


                // Create intent to move to HomePage with eventList
                val intent = Intent(this, HomePage::class.java)
                //intent.putParcelableArrayListExtra("event_list", eventList)
                startActivity(intent)
            }


        }


    private fun showTimePicker(onTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Create a new TimePickerDialog
        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val time = String.format("%02d:%02d", selectedHour, selectedMinute)
                onTimeSelected(time)
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }


        private fun openImagePicker() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    //@Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            imageView.setImageURI(imageUri)
        }
    }

}