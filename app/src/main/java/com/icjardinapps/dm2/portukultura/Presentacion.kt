package com.icjardinapps.dm2.portukultura

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Presentacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.presentacion)
        val boton=findViewById<Button>(R.id.btnRegistrarse)//El boton de registrarse ahora no va a llevar a registrarse
        boton.setOnClickListener{
            val intent= Intent(this@Presentacion,Mapa::class.java)
            startActivity(intent)
        }
    }
}