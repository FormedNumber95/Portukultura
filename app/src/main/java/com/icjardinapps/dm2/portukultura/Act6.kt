package com.icjardinapps.dm2.portukultura

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

/**
 * Act6 represents an activity that manages a simple interactive exercise.
 * It involves hiding and showing UI elements based on user interactions.
 */

class Act6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act6)

        // References to UI elements
        val imagenInicial = findViewById<ImageView>(R.id.imagenInicial)
        val botonContinuar = findViewById<Button>(R.id.botonContinuar)
        val contenedorPreguntas = findViewById<LinearLayout>(R.id.contenedorPreguntas)

        // Configure the continue button
        botonContinuar.setOnClickListener {
            // Hide the initial image and button
            imagenInicial.visibility = View.GONE
            botonContinuar.visibility = View.GONE

            // Show the questions container
            contenedorPreguntas.visibility = View.VISIBLE
        }

        val contenedorEjercicio = findViewById<LinearLayout>(R.id.contenedorEjercicio)
        contenedorEjercicio.visibility = View.VISIBLE // Show the exercise

// Images
        val imagen1 = findViewById<ImageView>(R.id.imagen1)
        val imagen2 = findViewById<ImageView>(R.id.imagen2)
        val imagen3 = findViewById<ImageView>(R.id.imagen3)
        val imagen4 = findViewById<ImageView>(R.id.imagen4)

// Texts
        val texto1 = findViewById<TextView>(R.id.texto1)
        val texto2 = findViewById<TextView>(R.id.texto2)
        val texto3 = findViewById<TextView>(R.id.texto3)
        val texto4 = findViewById<TextView>(R.id.texto4)

        /**
         * Displays the final image after completing the exercise.
         */
        fun showResult() {
            // Hide the exercise container
            val contenedorEjercicio = findViewById<LinearLayout>(R.id.contenedorEjercicio)
            contenedorEjercicio.visibility = View.GONE

            // Show the final image
            val imagenFinal = findViewById<ImageView>(R.id.imagenFinal)
            imagenFinal.visibility = View.VISIBLE
        }


    }
}