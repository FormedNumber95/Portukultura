package com.icjardinapps.dm2.portukultura

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Clase que representa la actividad de imagen de la fase 6.
 * Permite al usuario avanzar a la siguiente actividad.
 *
 * @author Diego
 */
class Act6Imagen : AppCompatActivity() {

    /**
     * Inicializa la actividad y configura el botón de continuar.
     *
     * @param savedInstanceState estado guardado de la actividad.
     * @author Diego
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act6_imagen) // Reemplazar con el nombre correcto del archivo XML

        val continueButton: Button = findViewById(R.id.btnContinuar)

        // Configura el botón para navegar a la siguiente actividad
        continueButton.setOnClickListener {
            val intent = Intent(this, Act6Preguntas::class.java)
            startActivity(intent)

            finish()
        }
    }
}
