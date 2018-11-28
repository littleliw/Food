package com.example.jack.food

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start.setOnClickListener {
            val intent = Intent(this,AddFood::class.java )
            startActivity(intent)
        }

        setting.setOnClickListener {

            val intent = Intent(this,Setting::class.java)
            startActivity(intent)
        }
        mainpage.setOnClickListener {
            val intent = Intent(this,MainApp::class.java)
            startActivity(intent)
        }

            }
}
