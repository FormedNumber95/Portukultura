package com.icjardinapps.dm2.portukultura

import android.os.CountDownTimer

class TimerController(
    private val totalTime: Long, // Tiempo total en milisegundos
    private val onTickCallback: (String) -> Unit, // Actualiza la interfaz con el tiempo restante
    private val onFinishCallback: () -> Unit // Acción al terminar el timer
) {
    private var timer: CountDownTimer? = null

    fun start() {
        timer?.cancel() // Asegura que no haya un timer previo corriendo
        timer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                onTickCallback(String.format("%02d:%02d", minutes, seconds))
            }

            override fun onFinish() {
                onFinishCallback()
            }
        }.start()
    }
}
/**
 * Clase para encapsular la configuración del temporizador y la acción del botón.
 */
data class TimerConfig(
    val timeMillis: Long,
    val buttonAction: () -> Unit
)