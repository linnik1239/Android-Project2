package com.example.project2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project2.R
import kotlinx.android.synthetic.main.activity_options.*

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)


        init()
    }



    private fun init(){
        preperties_Button.setOnClickListener {
            startActivity(Intent(this,PropertiesActivity::class.java))

        }

        to_do_Button.setOnClickListener {
            startActivity(Intent(this,ToDoListActivity::class.java))

        }



    }
}