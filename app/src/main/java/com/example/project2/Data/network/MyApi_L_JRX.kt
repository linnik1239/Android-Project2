package com.example.project2.Data.network


import com.example.practice20mvvm.models.User
import com.example.project2.Models.Property
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface MyApi_L_JRX {

    @POST("auth/login")
    fun login(
        @Body user: User
//        @Field("email") email: String,
//        @Field("password") password: String,
    ): Single<JsonElement>
    // : Call<ResponseBody>


    @POST("property")
    fun sendProperty(
        @Body property: Property
//        @Field("email") email: String,
//        @Field("password") password: String,
    ): Single<ResponseBody>


    @GET("property")
    fun getProperties(
    ): Single<JsonElement>



    @POST("upload/property/picture")
    open fun postImage(
        @Part("image\"; filename=\"myfile.jpg\" ") file: RequestBody?,
        @Part("desc") desc: RequestBody?
    ): Single<ResponseBody>


    @POST("my/files/photo/")
    fun uploadPhoto(
        @Header("Content-Type") contentType: String?,
        @Header("Authorization") auth: String?,
        @Body body: MultipartBody?
    ): Single<ResponseBody>



    @Multipart
    @POST("upload/property/picture")
    fun uploadPhoto2(
        @Header("Content-Type") contentType: String?,
        @Header("Authorization") auth: String?,
        @Body() body: MultipartBody?
    ): Call<JsonObject>


    @Multipart
    @POST("/app/uploadFile.do")
    fun uploadFile(
        @PartMap partMap: LinkedHashMap<String?, RequestBody?>?,
        @Part names: List<MultipartBody.Part?>?
    ): Call<JsonObject?>?


    @Multipart
    @POST("upload/property/picture")
    fun uploadImage4(
        @Part("image\"; filename=\"myfile.jpg\" ") file: RequestBody?,
        @Part("desc") desc: RequestBody?
    ): Call<JsonObject>


//    fun postImage(
//      //  @Body data: MyDatabase,
//        @Part("image") file: RequestBody,
//        //@Part("desc") desc: RequestBody
//
//
//    ): Single<ResponseBody>



    companion object{
        operator fun invoke(): MyApi_L_JRX{
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://apolis-property-management.herokuapp.com/api/")
                .build()
                .create(MyApi_L_JRX::class.java)
        }
//        operator fun invoke(): MyApi{
//            return Retrofit.Builder()
//                .baseUrl("https://apolis-property-management.herokuapp.com/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(MyApi::class.java)
//        }





     //   Response{protocol=http/1.1, code=404, message=Not Found, url=https://apolis-property-management.herokuapp.com/api/auth/login}

    }





}