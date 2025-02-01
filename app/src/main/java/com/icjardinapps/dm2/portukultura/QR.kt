package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

/**
 * Clase QR que maneja la funcionalidad relacionada con el código QR y su interacción.
 *
 * @author Aketza
 * @version 1.3
 */
class QR : AppCompatActivity() {
    /**
     * Metodo llamado al crear la actividad.
     * Configura la vista y maneja la interacción con el código QR y la conexion con la base de datos.
     *
     * @author Aketza
     * @param savedInstanceState Información sobre el estado previo de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        val bd=BD(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr)
        val img:ImageView=findViewById(R.id.imgQR)
        img.setOnClickListener {
            img.setImageResource(R.drawable.fin)
            Thread {
                var usuario=intent.getStringExtra("nombre").toString() + intent.getStringExtra("apellido")
                    .toString()
                var insertSuccess: Boolean = bd.guardarAlumnoEnMariaDB(
                    usuario,
                    intent.getStringExtra("nombre")
                        .toString() + " " + intent.getStringExtra("apellido").toString(),
                    1
                )
                if (!insertSuccess) {
                    var num = 1
                    do {
                        insertSuccess = bd.guardarAlumnoEnMariaDB(
                            usuario + num,
                            intent.getStringExtra("nombre")
                                .toString() + " " + intent.getStringExtra("apellido").toString(),
                            1
                        )
                        num++
                    } while (!insertSuccess)
                    usuario+=(num-1)
                }
                //ya esta el usuario deseado
                bd.guardarPuntuacion(usuario)
            }.start()
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
            textoAyuda.text = getString(R.string.qr_a_klikatu_behar_du_irudia_ikusteko)
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