package com.example.project2.Data.network
import com.example.practice20mvvm.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single

interface MyApi {

    @POST("auth/login")
    fun login(
         @Body user: User
//        @Field("email") email: String,
//        @Field("password") password: String,
    ): Call<ResponseBody>
   // : Call<ResponseBody>   Single




    companion object{
//        operator fun invoke(): MyApi{
//            return Retrofit.Builder()
//                .baseUrl("https://apolis-property-management.herokuapp.com/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(MyApi::class.java)
//        }



        operator fun invoke(): MyApi{
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://apolis-property-management.herokuapp.com/api/")
                .build()
                .create(MyApi::class.java)
        }






    }





}