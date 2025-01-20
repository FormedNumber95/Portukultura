package com.icjardinapps.dm2.portukultura

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
/**
 * Clase QR que maneja la funcionalidad relacionada con el código QR y su interacción.
 *
 * @author Aketza
 * @version 1.0
 */
class QR : AppCompatActivity() {
    /**
     * Metodo llamado al crear la actividad.
     * Configura la vista y maneja la interacción con el código QR.
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
    }
}