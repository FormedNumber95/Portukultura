package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import kotlin.system.exitProcess

/**
 * Clase Presentacion que representa la actividad inicial de la aplicación.
 * Muestra una interfaz donde el usuario puede ingresar sus datos para registrarse.
 * También proporciona funcionalidades adicionales como la visualización de las credenciales
 * de los autores y el cambio de idioma.
 *
 * @author Intissar
 * @version 1.4
 */
class Presentacion : AppCompatActivity() {
    private lateinit var credenciales: ImageView
    private lateinit var idioma: ImageView
    private lateinit var nombre: String
    private lateinit var apellido: String

    /**
     * Metodo llamado al crear la actividad.
     * Configura la vista inicial, asigna listeners a los botones y configura funcionalidades
     * adicionales como el cuadro de diálogo de registro y la ayuda.
     *
     * @param savedInstanceState Si la actividad se está recreando desde un estado anterior,
     * este parámetro contiene los datos más recientes suministrados.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.presentacion)

        // Obtener referencia al botón "Registrarse" y asignar un listener
        val boton: Button = findViewById(R.id.btnRegistrarse)
        boton.setOnClickListener {
            showInputDialog()
        }

        // Configurar la funcionalidad del botón de ayuda
        configurarAyuda()
         credenciales = findViewById(R.id.credenciales)
        // Configurar los botones de "credenciales" e "idioma"
        credenciales.setOnClickListener {
            mostrarCredenciales()
        }
        idioma = findViewById(R.id.idioma)
        idioma.setOnClickListener {
            cambiarIdioma()
        }
    }

    /**
     * Muestra un cuadro de diálogo personalizado para que el usuario ingrese su nombre y apellido.
     * Valida los datos ingresados y, si son correctos, inicia la actividad Mapa con los datos.
     *
     * @author Intissar
     */
    private fun showInputDialog() {
        // Inflar el diseño del cuadro de diálogo
        val inflater = LayoutInflater.from(this)
        val dialogView: View = inflater.inflate(R.layout.ventana_emergente, null)

        // Obtener referencias a los campos de texto del cuadro de diálogo
        val editTextNombre: EditText = dialogView.findViewById(R.id.editTextText)
        val editTextApellido: EditText = dialogView.findViewById(R.id.editTextText2)

        // Crear el cuadro de diálogo
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setTitle(getString(R.string.dialog_title))
        builder.setPositiveButton(getString(R.string.dialog_comenzar), null)
        builder.setNegativeButton(getString(R.string.dialog_cancelar)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()

        // Configurar el listener del botón "Comenzar"
        dialog.setOnShowListener {
            val buttonComenzar = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            buttonComenzar.setOnClickListener {
                nombre = editTextNombre.text.toString().trim()
                apellido = editTextApellido.text.toString().trim()

                if (nombre.isEmpty() || apellido.isEmpty()) {
                    // Mostrar mensaje de error si los campos están vacíos
                    Toast.makeText(this, getString(R.string.error_campos_vacios), Toast.LENGTH_SHORT).show()
                } else {
                    // Iniciar la actividad Mapa con los datos ingresados
                    val intent = Intent(this, Mapa::class.java)
                    intent.putExtra("nombre", nombre)
                    intent.putExtra("apellido", apellido)
                    startActivity(intent)
                    // Cerrar el cuadro de diálogo
                    dialog.dismiss()
                    finish()
                }
            }
        }

        // Mostrar el cuadro de diálogo
        dialog.show()
    }

    /**
     * Configura el botón de ayuda para mostrar una ventana emergente con instrucciones.
     * La ventana incluye un botón para cerrar la ventana de ayuda.
     *
     * @author Intissar
     */
    private fun configurarAyuda() {
        val imagenAyuda: ImageView = findViewById(R.id.ayuda)
        imagenAyuda.setOnClickListener {
            // Crear y mostrar la ventana emergente de ayuda
            val ayudaView = layoutInflater.inflate(R.layout.ayuda, null)
            val ayudaDialog = androidx.appcompat.app.AlertDialog.Builder(this)
                .setView(ayudaView)
                .create()
            ayudaDialog.show()

            // Configurar el texto de ayuda
            val textoAyuda: TextView = ayudaView.findViewById(R.id.ayudaTexto)
            textoAyuda.text = getString(R.string.ongi_etorri_jokoaren_aurkezpenera_testua_irakurri_eta_ulertu_ondoren_erregistratu_botoian_klik_egin_dezakezu_jolasten_hasi_eta_puntu_irabazteko)

            // Configurar el botón "cerrar" para cerrar la ventana de ayuda
            val cerrar: Button = ayudaView.findViewById(R.id.cerrar)
            cerrar.setOnClickListener {
                ayudaDialog.dismiss()
            }
        }
    }

    /**
     * Muestra un cuadro de diálogo con las credenciales de los autores.
     * Esta función se ejecuta cuando el usuario hace clic en el botón de "Credenciales".
     * Muestra los nombres de los autores.
     *
     * @author Intissar
     * @see AlertDialog.Builder
     */
    private fun mostrarCredenciales() {
        val mensaje = getString(R.string.desarrolladores)+
                "\n"+
                "Intissar Balouk Anakar,\n" +
                "Aketza Gonzalez Rey\n" +
                "Diego Fernandez Garcia\n"+
                "\n"+
                getString(R.string.clientes)+
                "\n"+
                "Goizane Barragán \n"+
                "Naia Bravo de Medina \n"+
                "Nerea Camacho \n"+
                "Oihane Fernández\n"+
                "Maialen Fernandez  \n"
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.credenciales))
        builder.setMessage(mensaje)
        builder.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    /**
     * Función para cambiar el idioma de la aplicación.
     *
     * @author Aketza
     */
    fun cambiarIdioma() {
        // Obtener el idioma actual desde SharedPreferences
        val prefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val idiomaActual = prefs.getString("Idioma", "eu")  // Default: euskera

        // Determinar el nuevo idioma
        val nuevoIdioma = when (idiomaActual) {
            "es" -> "eu" // Cambiar a euskera
            "eu" -> "es" // Cambiar a español
            else -> "eu"
        }

        // Guardar el nuevo idioma en SharedPreferences
        prefs.edit().putString("Idioma", nuevoIdioma).apply()

        // Cambiar el Locale de la aplicación
        val locale = Locale(nuevoIdioma)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)

        // Reiniciar la actividad para aplicar los cambios
        recreate()
    }


    /**
     * Sobrescribe el comportamiento del botón de retroceso.
     * Actualmente no realiza ninguna acción al presionar el botón de retroceso.
     *
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
