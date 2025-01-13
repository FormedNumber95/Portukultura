package com.icjardinapps.dm2.portukultura

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class Video : AppCompatActivity() {

    private lateinit var timerController: TimerController
    private lateinit var btnAurrera: Button
    private lateinit var videoView:VideoView

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
    }

    /**
     * Devuelve la configuración del temporizador, la acción del botón y el video según el índice del marcador.
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
                timeMillis = 6000, // 6 segundos
                buttonAction = {
                    val intent = Intent(this, SopaDeLetras::class.java)
                    startActivity(intent)
                    finish()
                }
            )
            //Actividad 2
            1 -> TimerConfig(
                timeMillis = 10000, // 10 segundos
                buttonAction = {
                    val intent = Intent(this, Puzzle::class.java)
                    startActivity(intent)
                    finish()
                }
            )
            //Actividad 3
            2 -> TimerConfig(
                timeMillis = 15000, // 15 segundos
                buttonAction = {
                    val intent = Intent(this, TrenGeltokiaGalderaErrepikagarriak::class.java)
                    startActivity(intent)
                    finish()
                }
            )
            //Actividad 5
            4 -> TimerConfig(
                timeMillis = 12000, // 12 segundos
                buttonAction = {
                    val intent = Intent(this, Ejer5::class.java)
                    startActivity(intent)
                    finish()
                }
            )
            //Actividad 7
            else -> TimerConfig(
                timeMillis = 20000, // 20 segundos
                buttonAction = {
                    val intent = Intent(this, Abestia::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}