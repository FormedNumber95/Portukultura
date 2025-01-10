package com.icjardinapps.dm2.portukultura

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrdenarPalabra : AppCompatActivity() {

    private val letras:Array<Char> = arrayOf('O','N','D','A','R','E','A')

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ordenar_palabra)
        //Las letras para agarrar
        val letra1:TextView=findViewById(R.id.letra1)
        val letra2:TextView=findViewById(R.id.letra2)
        val letra3:TextView=findViewById(R.id.letra3)
        val letra4:TextView=findViewById(R.id.letra4)
        val letra5:TextView=findViewById(R.id.letra5)
        val letra6:TextView=findViewById(R.id.letra6)
        val letra7:TextView=findViewById(R.id.letra7)
        //La posicion donde va cada letra
        val primeraLetra:TextView=findViewById(R.id.primeraLetra)
        val segundaLetra:TextView=findViewById(R.id.segundaLetra)
        val terceraLetra:TextView=findViewById(R.id.terceraLetra)
        val cuartaLetra:TextView=findViewById(R.id.cuartaLetra)
        val quintaLetra:TextView=findViewById(R.id.quintaLetra)
        val sextaLetra:TextView=findViewById(R.id.sextaLetra)
        val septimaLetra:TextView=findViewById(R.id.septimaLetra)
        //Asignar las letras con una letra aleatoria
        letras.shuffle()
        letra1.text=letras[0].toString()
        letra2.text=letras[1].toString()
        letra3.text=letras[2].toString()
        letra4.text=letras[3].toString()
        letra5.text=letras[4].toString()
        letra6.text=letras[5].toString()
        letra7.text=letras[6].toString()
    }
}