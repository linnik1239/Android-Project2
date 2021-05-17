package com.example.project2.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.example.project2.R
import com.example.project2.SessionManager.SessionManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var sessionManager:SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionManager = SessionManager(this)




    }


    override fun onResume() {
        super.onResume()

       // Thread.sleep(7_000)



        CoroutineScope(Dispatchers.IO).launch {
            delay(TimeUnit.SECONDS.toMillis(3))
            withContext(Dispatchers.Main) {
                if(sessionManager.isLoggedIN()){
                   // applicationContext
                     startActivity(Intent(applicationContext,OptionsActivity::class.java))
                   // startActivity(Intent(applicationContext,ChoiceActivity::class.java))
                }else{
                    startActivity(Intent(applicationContext,ChoiceActivity::class.java))
                }

                finish()
            }
        }



    }

}