package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
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
 * Clase principal de la aplicación que representa un quiz con dos preguntas.
 * Reproduce un sonido y muestra un mensaje en caso de respuesta incorrecta.
 *
 * @author Intissar
 * @version 1.2
 */
class Ejer5 : AppCompatActivity() {

    private var question1AnsweredCorrectly = false
    private var question2AnsweredCorrectly = false
    private var animationDrawable: AnimationDrawable? =null

    /**
     * Metodo llamado al crear la actividad. Configura los grupos de preguntas y el manejo de respuestas.
     *
     * @author Intissar
     * @param savedInstanceState Estado guardado previamente de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejer5)
        val avatar = findViewById<ImageView>(R.id.avatar)
        avatar.setImageResource(R.drawable.animacion_hablar)
        animationDrawable = avatar.drawable as AnimationDrawable

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
                    avatar.setImageResource(R.drawable.animacion_hablar)
                    checkAllQuestionsCorrect()
                } else {
                    // Reproducir sonido y mostrar mensaje de error
                    playIncorrectSound(incorrectSound)
                    avatar.setImageResource(R.drawable.animacion_hablar)  // Reinicia la imagen
                    animationDrawable = avatar.drawable as AnimationDrawable
                    animationDrawable!!.start()
                    // Programa la detención después de 10 segundos
                    avatar.postDelayed({
                        animationDrawable!!.stop()
                        avatar.setImageResource(R.drawable.animacion_hablar_triste)
                        animationDrawable = avatar.drawable as AnimationDrawable
                    }, 3000) // 3000 milisegundos = 3 segundo
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
                    avatar.setImageResource(R.drawable.animacion_hablar)
                    checkAllQuestionsCorrect()
                } else {
                    // Reproducir sonido y mostrar mensaje de error
                    playIncorrectSound(incorrectSound)
                    avatar.setImageResource(R.drawable.animacion_hablar)  // Reinicia la imagen
                    animationDrawable = avatar.drawable as AnimationDrawable
                    animationDrawable!!.start()
                    // Programa la detención después de 10 segundos
                    avatar.postDelayed({
                        animationDrawable!!.stop()
                        avatar.setImageResource(R.drawable.animacion_hablar_triste)
                        animationDrawable = avatar.drawable as AnimationDrawable
                    }, 3000) // 3000 milisegundos = 3 segundo
                }
            }
        }
        configurarAyuda()
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
            // Configurar el ícono de ayuda
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
                    getString(R.string.helburu_finalerako_letra_bat_lortu_duzu_itzuli_mapa_atzera_nahi_baduzu_sakatu_mapa_botoia)
                // Configurar el botón "cerrar" para cerrar la ventana emergente
                val cerrar: Button = ayudaView.findViewById(R.id.cerrar)
                cerrar.setOnClickListener {
                    ayudaDialog.dismiss() // Cierra solo la ventana emergente
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
            textoAyuda.text = "Irakurri galderak arretaz eta aukeratu zure iritziz egokiena den aukera."

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
        if(!AppUtils.isAppInForeground(applicationContext)) {
            finishAffinity() // Cierra todas las actividades de la aplicación
        }
    }

    /**
     * Se llama cuando la actividad entra en estado detenido.
     * Este metodo finaliza el proceso de la aplicación de manera forzada.
     *
     * @author Aketza
     */
    override fun onStop() {
        super.onStop()
        if(!AppUtils.isAppInForeground(applicationContext)) {
            exitProcess(0) // Finaliza el proceso de la aplicación
        }
    }

}
