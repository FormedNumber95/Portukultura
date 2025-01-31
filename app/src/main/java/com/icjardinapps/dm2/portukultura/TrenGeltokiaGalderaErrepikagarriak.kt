package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

/**
 * Actividad principal para gestionar un cuestionario con preguntas.
 *
 * @author Aketza
 * @version 1.1
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
        configurarAyuda()
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
        Toast.makeText(this, getString(R.string.txarto_egin_duzu_saiatu_berriro), Toast.LENGTH_SHORT).show()
    }
    /**
     * Configura el botón de ayuda para mostrar una ventana emergente con instrucciones.
     * Incluye un botón "cerrar" para cerrar la ventana.
     *
     * @author Intissar
     */
    private fun configurarAyuda() {
        val imagenAyuda: ImageView = findViewById(R.id.ayuda)
        imagenAyuda.setOnClickListener {
            // Crear una ventana emergente
            val ayudaView = layoutInflater.inflate(R.layout.ayuda, null)
            val ayudaDialog = androidx.appcompat.app.AlertDialog.Builder(this)
                .setView(ayudaView)
                .create()
            ayudaDialog.show()
            // Cambiar el texto en el layout de ayuda
            val textoAyuda: TextView = ayudaView.findViewById(R.id.ayudaTexto)
            textoAyuda.text =
                getString(R.string.irakurri_galderak_arretaz_eta_aukeratu_zure_iritziz_egokiena_den_aukera)

            // Configurar el botón "cerrar" para cerrar la ventana emergente
            val cerrar: Button = ayudaView.findViewById(R.id.cerrar)
            cerrar.setOnClickListener {
                ayudaDialog.dismiss() // Cierra solo la ventana emergente
            }
        }
    }

    /**
     * Funcion vacia que elimina el uso del boton  de retroceso
     * @author Intissar
     */
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }

    /**
     * Se llama cuando la actividad entra en estado de pausa.
     * Este metodo cierra todas las actividades de la aplicación, finalizando su ejecución.
     *
     * @author Aketza
     */
    override fun onPause() {
        super.onPause()
        finishAffinity() // Cierra todas las actividades de la aplicación
    }

    /**
     * Se llama cuando la actividad entra en estado detenido.
     * Este metodo finaliza el proceso de la aplicación de manera forzada.
     *
     * @author Aketza
     */
    override fun onStop() {
        super.onStop()
        exitProcess(0) // Finaliza el proceso de la aplicación
    }
}
