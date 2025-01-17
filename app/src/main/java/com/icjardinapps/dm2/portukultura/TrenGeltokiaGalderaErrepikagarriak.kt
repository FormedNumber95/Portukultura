package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
/**
 * Actividad principal para gestionar un cuestionario con preguntas.
 *
 * @author Aketza
 */
class TrenGeltokiaGalderaErrepikagarriak : AppCompatActivity() {

    private var radio1Ok:Boolean=false
    private var radio2Ok:Boolean=false
    private var radio3Ok:Boolean=false

    /**
     * Configura la actividad al crearse.
     *
     * @author Aketza
     * @param savedInstanceState estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tren_geltokia_galdera_errepikagarriak)
        val incorrectSound = MediaPlayer.create(this, R.raw.txarto_egin)
        val radioGroupPregunta1 = findViewById<RadioGroup>(R.id.radioGroupPregunta1)
        val radioGroupPregunta2 = findViewById<RadioGroup>(R.id.radioGroupPregunta2)
        val radioGroupPregunta3 = findViewById<RadioGroup>(R.id.radioGroupPregunta3)

        radioGroupPregunta1.setOnCheckedChangeListener{ group, checkedId ->
            val selectedOption = findViewById<RadioButton>(checkedId)
            if (selectedOption != null) {
                if (selectedOption.id == R.id.rad1888) {
                    // Marcar la primera pregunta como respondida correctamente
                    radio1Ok = true
                    checkAllQuestionsCorrect()
                } else {
                    // Reproducir sonido y mostrar mensaje de error
                    playIncorrectSound(incorrectSound)
                }
            }
        }
        radioGroupPregunta2.setOnCheckedChangeListener{ group, checkedId ->
            val selectedOption = findViewById<RadioButton>(checkedId)
            if (selectedOption != null) {
                if (selectedOption.id == R.id.radAldeZaharrean) {
                    // Marcar la primera pregunta como respondida correctamente
                    radio2Ok = true
                    checkAllQuestionsCorrect()
                } else {
                    // Reproducir sonido y mostrar mensaje de error
                    playIncorrectSound(incorrectSound)
                }
            }
        }
        radioGroupPregunta3.setOnCheckedChangeListener{ group, checkedId ->
            val selectedOption = findViewById<RadioButton>(checkedId)
            if (selectedOption != null) {
                if (selectedOption.id == R.id.radBai) {
                    // Marcar la primera pregunta como respondida correctamente
                    radio3Ok = true
                    checkAllQuestionsCorrect()
                } else {
                    // Reproducir sonido y mostrar mensaje de error
                    playIncorrectSound(incorrectSound)
                }
            }
        }
    }

    /**
     * Verifica si ambas preguntas han sido respondidas correctamente y cierra la actividad si es el caso.
     *
     * @author Aketza
     */
    private fun checkAllQuestionsCorrect() {
        if (radio1Ok && radio2Ok && radio3Ok) {
            val intent = Intent(this, TrenGeltokiaAsmakizunak::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Reproduce un sonido de error y muestra un mensaje indicando una respuesta incorrecta.
     *
     * @author Aketza
     * @param mediaPlayer Instancia del reproductor de medios con el sonido cargado.
     */
    private fun playIncorrectSound(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
        Toast.makeText(this, "Txarto egin duzu. Saiatu berriro.", Toast.LENGTH_SHORT).show()
    }

    /**
     * Funcion vacia que elimina el uso del boton  de retroceso
     * @author Intissar
     */
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }
}
