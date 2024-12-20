package com.icjardinapps.dm2.portukultura

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Presentacion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.presentacion)

        val boton: Button = findViewById(R.id.btnRegistrarse)
        boton.setOnClickListener {
            // Mostrar el diálogo directamente
            showInputDialog()
        }
    }

    private fun showInputDialog() {
        // Inflar el diseño del diálogo personalizado
        val inflater = LayoutInflater.from(this)
        val dialogView: View = inflater.inflate(R.layout.ventana_emergente, null)

        // Crear los campos de texto del diálogo
        val editTextNombre: EditText = dialogView.findViewById(R.id.editTextText)
        val editTextApellido: EditText = dialogView.findViewById(R.id.editTextText2)

        // Crear el diálogo
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setTitle("Introduzca sus datos")
        builder.setPositiveButton("Comenzar", null) // Listener configurado después para validación
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()

        // Configurar el listener del botón "Comenzar" después de crear el diálogo
        dialog.setOnShowListener {
            val buttonComenzar = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            buttonComenzar.setOnClickListener {
                val nombre = editTextNombre.text.toString().trim()
                val apellido = editTextApellido.text.toString().trim()

                if (nombre.isEmpty() || apellido.isEmpty()) {
                    Toast.makeText(this, "Por favor, rellene todos los campos.", Toast.LENGTH_SHORT).show()
                } else {
                    // Guardar o procesar los datos (puedes añadir lógica adicional aquí)
                    Toast.makeText(this, "Datos guardados: $nombre $apellido", Toast.LENGTH_SHORT).show()

                    // Iniciar la actividad Main
                    val intent = Intent(this, Mapa::class.java)
                    startActivity(intent)
                    dialog.dismiss() // Cerrar el diálogo
                }
            }
        }

        // Mostrar el diálogo
        dialog.show()
    }
}
