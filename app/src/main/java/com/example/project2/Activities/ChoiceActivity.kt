package com.example.project2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project2.R
import kotlinx.android.synthetic.main.activity_choice.*

class ChoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

        button_choice_register.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        button_choice_login.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}