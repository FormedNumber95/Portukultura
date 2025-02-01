package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

/**
 * Clase que gestiona la reproducción de videos y la ejecución de temporizadores para realizar diferentes actividades.
 * El comportamiento del temporizador y la acción del botón varían según el índice del marcador recibido.
 *
 * @author Aketza
 * @version 1.3
 */
class Video : AppCompatActivity() {

    private lateinit var timerController: TimerController
    private lateinit var btnAurrera: Button
    private lateinit var videoView:VideoView
    /**
     * Metodo que se ejecuta al crear la actividad.
     *
     * @author Aketza
     * @param savedInstanceState instancia previa de la actividad (si la hay).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video)

        val txtTiempo: TextView = findViewById(R.id.txtTiempo)
        btnAurrera = findViewById(R.id.btnAurrera)
        btnAurrera.isEnabled = false
        videoView=findViewById(R.id.videoView)

        // Obtener el índice del marcador
        val markerIndex = intent.getIntExtra("MARKER_INDEX", -1)

        // Configurar tiempo del temporizador y acción del botón según el marcador
        val timerConfig = getTimerConfig(markerIndex)

        timerController = TimerController(
            totalTime = timerConfig.timeMillis,
            onTickCallback = { time ->
                txtTiempo.text = time
            },
            onFinishCallback = {
                btnAurrera.isEnabled = true
            }
        )
        timerController.start()
        btnAurrera.setOnClickListener {
            timerConfig.buttonAction()
        }
        configurarAyuda()
    }

    /**
     * Devuelve la configuración del temporizador, la acción del botón y el video según el índice del marcador.
     *
     * @author Aketza
     * @param markerIndex índice del marcador que determina el video y la actividad a realizar.
     * @return una instancia de [TimerConfig] con la duración del temporizador y la acción del botón.
     */
    private fun getTimerConfig(markerIndex: Int): TimerConfig {
        val videoResId = when (markerIndex) {
            0 -> R.raw.video_act1 // Video para Actividad 1
            1 -> R.raw.video_act2 // Video para Actividad 2
            2 -> R.raw.video_act3 // Video para Actividad 3
            4 -> R.raw.video_act5 // Video para Actividad 5
            else -> R.raw.video_act7   // Video por defecto para otras actividades
        }

        // Configurar el video en el VideoView
        val videoUri = "android.resource://$packageName/$videoResId"
        videoView.setVideoPath(videoUri)
        val mediaController=MediaController(this)
        videoView.setMediaController(mediaController)
        videoView.start()

        return when (markerIndex) {
            //Actividad 1
            0 -> TimerConfig(
                timeMillis = 81000, // 81 segundos = 1min 21s
                buttonAction = {
                    val intent = Intent(this, Act1::class.java)
                    startActivity(intent)
                    finish()
                }
            )
            //Actividad 2
            1 -> TimerConfig(
                timeMillis = 93000, // 93 segundos = 1min 33s
                buttonAction = {
                    val intent = Intent(this, Puzzle::class.java)
                    startActivity(intent)
                    finish()
                }
            )
            //Actividad 3
            2 -> TimerConfig(
                timeMillis = 555000, // 555 segundos = 9min 15s
                buttonAction = {
                    val intent = Intent(this, TrenGeltokiaGalderaErrepikagarriak::class.java)
                    startActivity(intent)
                    finish()
                }
            )
            //Actividad 5
            4 -> TimerConfig(
                timeMillis = 330000, // 330 segundos = 5min 30s
                buttonAction = {
                    val intent = Intent(this, Ejer5::class.java)
                    startActivity(intent)
                    finish()
                }
            )
            //Actividad 7
            else -> TimerConfig(
                timeMillis = 1000, // 145 segundos = 2min 25s
                buttonAction = {
                    val nombre=intent.getStringExtra("nombre").toString()
                    val apellido=intent.getStringExtra("apellido").toString()
                    val intent = Intent(this, Abestia::class.java)
                    intent.putExtra("nombre",nombre)
                    intent.putExtra("apellido",apellido)
                    startActivity(intent)
                    finish()
                }
            )
        }
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
                getString(R.string.bideoa_ikusi_ondoren_hasieratu_botoia_aktibatuko_da_hasteko)
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
    override fun onBackPressed() {}

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