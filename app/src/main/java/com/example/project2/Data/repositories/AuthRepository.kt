package com.example.project2.Data.repositories

import android.content.Context
import android.content.Intent
import com.example.project2.Data.network.MyApi
import com.example.project2.Data.network.MyApi_R
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practice20mvvm.models.User
import com.example.practice20mvvm.models.UserR
import com.example.project2.Activities.OptionsActivity
import com.example.project2.Data.network.MyApi_L_JRX
import com.example.project2.SessionManager.SessionManager
import com.google.gson.JsonElement
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
//import com.example.practice20mvvm.data.network.MyApi
//import com.example.practice20mvvm.data.network.MyApi_R
//import com.example.practice20mvvm.models.User
//import com.example.practice20mvvm.models.UserR
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//.subscribeOn(Schedulers.io())
//.observeOn(AndroidSchedulers.mainThread())
//.subscribeWith(getObserver())


class AuthRepository {


    fun userLogin(email:String, password:String):LiveData<JsonElement>{


        //fun userLogin(email:String, password:String):Boolean{
        var loginResponse = MutableLiveData<JsonElement>()
      //  var loginResponse2 = MutableLiveData<String>()


        var api = MyApi()

        var user = User(email,password)



        var x = MyApi_L_JRX().login(User(email,password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                 object : SingleObserver<JsonElement>{
//                     override fun onSubscribe(d: Disposable) {
//                        Log.d("abc","onSubscribe "+d.toString())
//                     }
//
//                     override fun onSuccess(t: JsonElement) {
//                         TODO("Not yet implemented")
//                     }
//
//                     override fun onError(e: Throwable) {
//                         TODO("Not yet implemented")
//                     }
                    override fun onSubscribe(d: Disposable) {
                        Log.d("abc","onSubscribe "+d.toString())
                    }
                    override fun onSuccess(t: JsonElement) {




                        Log.d("abc",t.toString())
                            Log.d("abc",t.toString())
                            //  if)()
                          //  t.string()
                            loginResponse.value = t
                           // loginResponse.value = "true"//t.toString()             //"login success"
                    }

                    override fun onError(e: Throwable) {
                        Log.d("abc","onError "+e.toString())
                    }
                }
            )

//        api.login(user)
//            .enqueue(object: Callback<ResponseBody> {
//                override fun onResponse(
//                    call: Call<ResponseBody>,
//                    response: Response<ResponseBody>
//                ) {
//
//                   //  response.
//
//                    Log.d("abc",response.body().toString())
//                    Log.d("abc",response.isSuccessful.toString())
//
//                  //  if)()
//
//                    loginResponse.value = response.isSuccessful.toString()             //"login success"
//
//                   // loginResponse.value = response.message()             //"login success"
//
//                }
//
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    loginResponse.value = t.message //"login failed"
//                }
//
//
//            })
        return loginResponse
     //return false

 }


    fun getAnswers():SingleObserver<ResponseBody>{
        return object : SingleObserver<ResponseBody>{
            override fun onSubscribe(d: Disposable) {
                TODO("Not yet implemented")
            }

            override fun onSuccess(t: ResponseBody) {
                TODO("Not yet implemented")
            }

            override fun onError(e: Throwable) {
                TODO("Not yet implemented")
            }

        }


    }







    fun userRegister(email:String,landlordEmail:String?,firstName:String, password:String,mobile:String):LiveData<JsonElement>{
        var loginResponse:MutableLiveData<JsonElement>?  = MutableLiveData<JsonElement>()

        var user = UserR(email,landlordEmail,firstName,password,mobile)

        var api_R = MyApi_R()

        Log.d("abc","The user: "+user.toString())

        api_R.register(user).
        enqueue(object: Callback<JsonElement>{
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                loginResponse?.value = response.body()
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                loginResponse = null
            }


        })
        return loginResponse!!
    }




}



