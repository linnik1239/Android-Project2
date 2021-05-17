package com.example.project2.ui.auth

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.project2.Data.repositories.AuthRepository
import com.example.project2.R
import kotlinx.android.synthetic.main.activity_register.view.*


class AuthViewModel(): ViewModel() {
    var userName :String?=null
    var email: String? = null
    var landlordEmail:String? = null

    var password: String? = null
    var type:String?=null
    var authListener: AuthListener? = null

    fun onLoginButtonClick(view: View){
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid email and password")
            return
        }
        var loginResponse = AuthRepository().userLogin(email!!, password!!)
        authListener?.onSuccess(loginResponse)
    }


    fun onRegisterButtonClick(view:View){
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid email and password for register")
            return
        }
        OnRadioButtonClicked(view)
        var registerResponse = AuthRepository().userRegister(email!!,landlordEmail,userName!!, password!!,type!!)
        authListener?.onSuccess(registerResponse)
       // authListener?.onSuccess(true)

    }


    fun OnRadioButtonClicked(view: View){
        if(view is RadioButton){
            val checked = view.isChecked
            when(view.getId()){
                R.id.radio_landlord ->{
                    if(checked){
                        type = "landlord"
                        landlordEmail=null
                        authListener?.changeVisibleStatus(View.GONE)
                    }
                }
                R.id.radio_tenant ->{
                    if(checked){
                        authListener?.changeVisibleStatus(View.VISIBLE)
                        type = "tenant"
                    }
                }
                else ->{
                    authListener?.changeVisibleStatus(View.VISIBLE)
                    type = "tenant"
                }
            }
        }
    }

}