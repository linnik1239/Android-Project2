package com.example.project2.ui.auth

import androidx.lifecycle.LiveData
import com.google.gson.JsonElement


interface AuthListener{
    fun onCreate()
    fun onSuccess(response: LiveData<JsonElement>) //LiveData<String>
    fun onFailure(message:String)
    fun changeVisibleStatus(status:Int)
}