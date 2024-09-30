package com.example.findoraapi
import com.example.findoraapi.Models.User
import com.example.findoraapi.Models.MyResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("register")
    fun registerUser(@Body user: User): Call<MyResponse>

    @POST("login")
    fun loginUser(@Body user: User): Call<MyResponse>
}
