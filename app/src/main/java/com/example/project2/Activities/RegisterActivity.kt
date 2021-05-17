package com.example.project2.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.project2.R
import com.example.project2.databinding.ActivityRegisterBinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.project2.ui.auth.AuthListener
import com.example.project2.ui.auth.AuthViewModel
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*


class RegisterActivity : AppCompatActivity(),AuthListener {

    lateinit var mDataBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_register)
        mDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_register)

        var viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        mDataBinding.authViewModel = viewModel
        viewModel.authListener = this




    }

    override fun onCreate() {
        Toast.makeText(this,"Reg onCreate",Toast.LENGTH_SHORT).show()
        Log.d("abc","Reg onCreate")
    }
//
    override fun onSuccess(response: LiveData<JsonElement>) {
        response.observe(this, Observer {
            Log.d("abc","Reg success "+it)

            startActivity(Intent(this,LoginActivity::class.java))

            Toast.makeText(this,"Reg success "+it,Toast.LENGTH_SHORT).show()


        })    }

//    override fun onSuccess(response: Boolean) {
//
//
//    }

    override fun onFailure(message: String) {
        Log.d("abc","Reg onFailure")

        Toast.makeText(this,"Reg onFailure",Toast.LENGTH_SHORT).show()
    }

    override fun changeVisibleStatus(status: Int) {
        edit_text_email_landlord_R.visibility = status
    }
}