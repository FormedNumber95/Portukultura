package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Clase principal de la aplicación que representa un quiz con dos preguntas.
 * Reproduce un sonido y muestra un mensaje en caso de respuesta incorrecta.
 *
 * @author Intissar
 */
class Ejer5 : AppCompatActivity() {

    private var question1AnsweredCorrectly = false
    private var question2AnsweredCorrectly = false

    /**
     * Metodo llamado al crear la actividad. Configura los grupos de preguntas y el manejo de respuestas.
     *
     * @author Intissar
     * @param savedInstanceState Estado guardado previamente de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejer5)

        // Cargar el archivo de sonido para respuestas incorrectas
        val incorrectSound = MediaPlayer.create(this, R.raw.txarto_egin)

        // Referencia al grupo de botones de la primera pregunta
        val question1Group = findViewById<RadioGroup>(R.id.question1_group)
        // Referencia al grupo de botones de la segunda pregunta
        val question2Group = findViewById<RadioGroup>(R.id.question2_group)

        // Configurar listener para las respuestas de la primera pregunta
        question1Group.setOnCheckedChangeListener { group, checkedId ->
            val selectedOption = findViewById<RadioButton>(checkedId)
            if (selectedOption != null) {
                if (selectedOption.id == R.id.q1_correct) {
                    // Marcar la primera pregunta como respondida correctamente
                    question1AnsweredCorrectly = true
                    checkAllQuestionsCorrect()
                } else {
                    // Reproducir sonido y mostrar mensaje de error
                    playIncorrectSound(incorrectSound)
                }
            }
        }

        // Configurar listener para las respuestas de la segunda pregunta
        question2Group.setOnCheckedChangeListener { group, checkedId ->
            val selectedOption = findViewById<RadioButton>(checkedId)
            if (selectedOption != null) {
                if (selectedOption.id == R.id.q2_correct) {
                    // Marcar la segunda pregunta como respondida correctamente
                    question2AnsweredCorrectly = true
                    checkAllQuestionsCorrect()
                } else {
                    // Reproducir sonido y mostrar mensaje de error
                    playIncorrectSound(incorrectSound)
                }
            }
        }
    }


    private fun checkAllQuestionsCorrect() {
        if (question1AnsweredCorrectly && question2AnsweredCorrectly) {
            setContentView(R.layout.letra) // Cambiar al diseño letra.xml

            // Modificar el texto de textLetra si está vacío
            val textLetra = findViewById<TextView>(R.id.textLetra)
            if (textLetra.text.isEmpty()) {
                textLetra.text = getString(R.string.r_lortu_duzue)
                // Configurar listener para el botón mapa
                val mapaButton = findViewById<Button>(R.id.mapa)
                mapaButton.setOnClickListener {
                    finish() // Cierra la actividad
                }
            }


        }

    }
    /**
     * Reproduce un sonido de error y muestra un mensaje indicando una respuesta incorrecta.
     *
     * @author Intissar
     * @param mediaPlayer Instancia del reproductor de medios con el sonido cargado.
     */
    private fun playIncorrectSound(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
        Toast.makeText(this, getString(R.string.txarto_egin_duzu_saiatu_berriro), Toast.LENGTH_SHORT).show()
    }

    /**
     * Funcion vacia que elimina el uso del boton  de retroceso
     * @author Intissar
     */
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }
}
