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

/**
 * Clase Presentacion que representa la actividad de presentación inicial de la aplicación.
 * Muestra una interfaz para que el usuario ingrese sus datos en un cuadro de diálogo.
 */
class Presentacion : AppCompatActivity() {

    /**
     * Metodo llamado al crear la actividad.
     * Configura la vista inicial y asigna un listener al botón para abrir el cuadro de diálogo.
     *
     * @param savedInstanceState Si la actividad se está recreando desde un estado anterior,
     * este parámetro contiene los datos más recientes suministrados.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.presentacion)

        // Obtener referencia al botón en la interfaz
        val boton: Button = findViewById(R.id.btnRegistrarse)

        // Asignar un listener para mostrar el cuadro de diálogo al hacer clic
        boton.setOnClickListener {
            showInputDialog()
        }
    }

    /**
     * Muestra un cuadro de diálogo personalizado para que el usuario introduzca su nombre y apellido.
     * Valida los datos ingresados y, si son correctos, inicia la actividad Mapa.
     */
    private fun showInputDialog() {
        // Inflar el diseño del cuadro de diálogo personalizado
        val inflater = LayoutInflater.from(this)
        val dialogView: View = inflater.inflate(R.layout.ventana_emergente, null)

        // Obtener referencias a los campos de texto del cuadro de diálogo
        val editTextNombre: EditText = dialogView.findViewById(R.id.editTextText)
        val editTextApellido: EditText = dialogView.findViewById(R.id.editTextText2)

        // Crear el cuadro de diálogo
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setTitle("Introduzca sus datos")
        builder.setPositiveButton("Comenzar", null) // Listener configurado después para validación
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss() // Cierra el cuadro de diálogo si se cancela
        }

        val dialog = builder.create()

        // Configurar el listener del botón "Comenzar" después de crear el cuadro de diálogo
        dialog.setOnShowListener {
            val buttonComenzar = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            buttonComenzar.setOnClickListener {
                val nombre = editTextNombre.text.toString().trim()
                val apellido = editTextApellido.text.toString().trim()

                if (nombre.isEmpty() || apellido.isEmpty()) {
                    // Mostrar un mensaje de error si los campos están vacíos
                    Toast.makeText(this, "Por favor, rellene todos los campos.", Toast.LENGTH_SHORT).show()
                } else {
                    // Mostrar un mensaje de éxito con los datos ingresados
                    Toast.makeText(this, "Datos guardados: $nombre $apellido", Toast.LENGTH_SHORT).show()

                    // Crear un Intent para iniciar la actividad Mapa
                    val intent = Intent(this, Mapa::class.java)
                    startActivity(intent)

                    // Cerrar el cuadro de diálogo
                    dialog.dismiss()
                }
            }
        }

        // Mostrar el cuadro de diálogo
        dialog.show()
    }
}
