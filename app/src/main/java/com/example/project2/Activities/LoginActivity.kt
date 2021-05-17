package com.example.project2.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.practice20mvvm.models.UserR
import com.example.project2.Data.network.MyApi_L_JRX
import com.example.project2.R
import com.example.project2.SessionManager.SessionManager
import com.example.project2.databinding.ActivityLoginBinding
import com.example.project2.databinding.ActivityMainBinding
//import com.example.project2.databinding.ActivityMainBinding
import com.example.project2.ui.auth.AuthListener
import com.example.project2.ui.auth.AuthViewModel
import com.google.gson.JsonElement
import org.json.JSONObject

//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import com.example.project2.R
////import com.example.project2.databinding.ActivityLoginBinding
//
//import android.database.DatabaseUtils
//import android.util.Log
//import android.widget.Toast
//import androidx.lifecycle.ViewModelProviders
//import com.example.project2.databinding.ActivityLoginBinding
//import com.example.project2.ui.auth.AuthListener
//import com.example.project2.ui.auth.AuthViewModel


class LoginActivity : AppCompatActivity(), AuthListener {
    lateinit var mDataBinding: ActivityLoginBinding
    lateinit var sessionManager: SessionManager



    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // binding






        //setContentView(R.layout.activity_login)
//
//        mDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
//
//        var viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
//        mDataBinding.authViewModel = viewModel
//        viewModel.authListener = this




        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        var viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        mDataBinding.authViewModel = viewModel
        viewModel.authListener = this





    }

    override fun onCreate() {
        Toast.makeText(this,"Log onCreate",Toast.LENGTH_SHORT).show()
        Log.d("abc","Log onCreate")    }

    override fun onSuccess(response: LiveData<JsonElement>) { //LiveData<String>

//        if(response){
//            startActivity(Intent(this, OptionsActivity::class.java))
//
//        }

        response.observe(this, Observer {
            Log.d("abc","Log success "+it)



       //     this.mDataBinding.authViewModel.

            if(it !=null){


                var ob = it.asJsonObject.get("user").asJsonObject  //it.getJSONObject("user")


                var firstName = ob.getAsJsonPrimitive("name")?.toString()
                var _id = ob.getAsJsonPrimitive("_id")?.toString()

                var landlordEmail:String?=  ob.getAsJsonPrimitive("landlordEmail")?.toString()

                var email = ob.getAsJsonPrimitive("email")?.toString()
                var type = ob.getAsJsonPrimitive("type")?.toString()
                var password = ob.getAsJsonPrimitive("password")?.toString()



                Log.d("abc",_id.toString())
                var user: UserR = UserR(firstName,email,landlordEmail,password,type)

                var sessionManager = SessionManager(this)
                sessionManager.register(user)
                sessionManager.setLogin(true)


                startActivity(Intent(this, OptionsActivity::class.java))
            }

          //  startActivity(Intent(this, OptionsActivity::class.java))
            Toast.makeText(this,"Log success "+it,Toast.LENGTH_SHORT).show()

        })
    }

    override fun onFailure(message: String) {
        Log.d("abc","Log onFailure")

        Toast.makeText(this,"Log onFailure", Toast.LENGTH_SHORT).show()
    }

    override fun changeVisibleStatus(status: Int) {

    }
}