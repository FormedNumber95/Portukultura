package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess

/**
 * Clase que representa la actividad de preguntas de la fase 6.
 * Permite al usuario avanzar a la siguiente actividad.
 *
 * @author Diego
 * @version 1.2
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
        configurarAyuda()
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
            textoAyuda.text = getString(R.string.irakurri_galderak_arretaz_eta_erantzun_hauei)
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
