package com.example.multinativeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var buttonStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Log.d("WelcomeActivity", "onCreate: Iniciando Welcome Activity")

        buttonStart = findViewById(R.id.bt_get_in)
        buttonStart.setOnClickListener{
            //redireccionamiento
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("WelcomeActivity", "onStart: Welcome Activity está en primer plano")
    }

    override fun onPause() {
        super.onPause()
        Log.d("WelcomeActivity", "onPause: Welcome Activity está pausada")
    }

    override fun onStop() {
        super.onStop()
        Log.d("WelcomeActivity", "onStop: Welcome Activity está pausada")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("WelcomeActivity", "onDestroy: Welcome Activity está destruida")
    }
}