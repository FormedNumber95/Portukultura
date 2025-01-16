package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Clase que representa la actividad de preguntas de la fase 6.
 * Permite al usuario avanzar a la siguiente actividad.
 *
 * @author Diego
 */
class Act6Preguntas : AppCompatActivity() {
    /**
     * Inicializa la actividad y configura el botón de continuar.
     *
     * @author Diego
     * @param savedInstanceState estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act6_preguntas) // Reemplazar con el nombre correcto del archivo XML

        val continueButton: Button = findViewById(R.id.btnContinuar)

        // Configura el botón para navegar a la siguiente actividad
        continueButton.setOnClickListener {
            val intent = Intent(this, Act6DnD::class.java)
            startActivity(intent)

            finish()
        }
    }
    /**
     * Funcion vacia que elimina el uso del boton  de retroceso
     * @author Intissar
     */
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {}
}
