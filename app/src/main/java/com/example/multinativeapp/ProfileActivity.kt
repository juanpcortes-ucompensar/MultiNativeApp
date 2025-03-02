package com.example.multinativeapp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ProfileActivity : AppCompatActivity() {

    private lateinit var textViewName: TextView
    private lateinit var textViewLastName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var buttonEdit: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Log.d("ProfileActivity", "onCreate: Iniciando Activity Profile")

        //Inicializar las variables
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        textViewName = findViewById(R.id.tv_name)
        textViewLastName = findViewById(R.id.tv_lastname)
        textViewEmail = findViewById(R.id.tv_email)
        textViewPhone = findViewById(R.id.tv_phone)
        buttonEdit = findViewById(R.id.bt_edit)

        llenarDatosUsuario()

        buttonEdit.setOnClickListener{
            //Codigo para editar el perfil
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("ProfileActivity", "onStart: Activity Profile está en primer plano")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ProfileActivity", "onPause: Activity Profile está pausada")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ProfileActivity", "onStop: Activity Profile está pausada")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ProfileActivity", "onDestroy: Activity Profile está destruida")
    }

    private fun llenarDatosUsuario() {
        val nombres = sharedPreferences.getString("nombre","")
        val apellido = sharedPreferences.getString("apellido","")
        val correo = sharedPreferences.getString("correo","")
        val telefono = sharedPreferences.getString("telefono","")

        textViewName.setText("Nombres: " + nombres)
        textViewLastName.setText("Apellidos: " + apellido)
        textViewEmail.setText("Correo: " + correo)
        textViewPhone.setText("Telefono: " + telefono)
    }

}