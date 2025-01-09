package com.icjardinapps.dm2.portukultura

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Video : AppCompatActivity() {

    private lateinit var timerController: TimerController
    private lateinit var btnAurrera: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video)

        val txtTiempo: TextView = findViewById(R.id.txtTiempo)
        btnAurrera = findViewById(R.id.btnAurrera)
        btnAurrera.isEnabled = false

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
     * Devuelve la configuración del temporizador y acción del botón según el índice del marcador.
     */
    private fun getTimerConfig(markerIndex: Int): TimerConfig {
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