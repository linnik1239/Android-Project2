package com.example.project2.Data.network

import com.example.practice20mvvm.models.UserR
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApi_R {

    @POST("auth/register")
    fun register(
        @Body user: UserR
//        @Field("email") email: String,
//        @Field("password") password: String,
    )
            : Call<JsonElement>




    companion object{
        operator fun invoke(): MyApi_R{
            return Retrofit.Builder()
                .baseUrl("https://apolis-property-management.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi_R::class.java)
        }
    }

}