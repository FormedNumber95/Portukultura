package com.icjardinapps.dm2.portukultura
import android.content.Intent
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.system.exitProcess

/**
 * Actividad principal para gestionar la edición y validación de textos en un entorno interactivo.
 *
 * @author Aketza
 * @version 1.3
 */
class Abestia : AppCompatActivity() {

    private lateinit var subeArriba: TextView
    private lateinit var queMeMareo: TextView
    private lateinit var pantorrilla: TextView
    private lateinit var chiquitin: TextView
    private lateinit var startButton: Button

    private var isEditing = false
    /**
     * Configura la actividad al crearse.
     *
     * @author Aketza
     * @param savedInstanceState estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.abestia)

        subeArriba = findViewById(R.id.sube_arriba)
        queMeMareo = findViewById(R.id.que_me_mareo)
        pantorrilla = findViewById(R.id.pantorrilla)
        chiquitin = findViewById(R.id.chiquitin)
        startButton = findViewById(R.id.hasi)

        startButton.setOnClickListener {
            if (!isEditing) {
                switchToEditMode()
            } else {
                validateInputs()
            }
        }
        configurarAyuda()
    }
    /**
     * @author Aketza
     * Cambia los TextView a EditText para permitir la edición de textos.
     */
    private fun switchToEditMode() {
        replaceTextViewWithEditText(subeArriba, "1")
        replaceTextViewWithEditText(queMeMareo, "2")
        replaceTextViewWithEditText(pantorrilla, "3")
        replaceTextViewWithEditText(chiquitin, "4")

        startButton.text = getString(R.string.comprobar)
        isEditing = true
    }
    /**
     * Valida los textos ingresados por el usuario.
     *
     * Si los textos son correctos, muestra un mensaje de éxito y cambia a la vista correspondiente.
     * Además, muestra la letra correspondiente y configura el botón de mapa para regresar a la actividad anterior.
     * Al hacer clic en el ícono de ayuda, se muestra una ventana emergente con información sobre el juego.
     *
     * @author Aketza, Intissar
     */
    private fun validateInputs() {
        val subeArribaInput = findViewById<EditText>(pantorrilla.id).text.toString()
        val queMeMareoInput = findViewById<EditText>(chiquitin.id).text.toString()
        val pantorrillaInput = findViewById<EditText>(subeArriba.id).text.toString()
        val chiquitinInput = findViewById<EditText>(queMeMareo.id).text.toString()
        // Validar los textos ingresados
        if (subeArribaInput.trim().lowercase().equals("sube arriba") && queMeMareoInput.trim().lowercase().equals("que me mareo") &&
            pantorrillaInput.trim().lowercase().equals("pantorrilla") && (chiquitinInput.trim().lowercase().equals("chiquitin")||chiquitinInput.trim().lowercase().equals("chiquitín"))) {

            setContentView(R.layout.letra) // Cambiar al diseño letra.xml

            // Modificar el texto de textLetra si está vacío
            val textLetra = findViewById<TextView>(R.id.textLetra)
            if (textLetra.text.isEmpty()) {
                textLetra.text = getString(R.string.a_lortu_duzue)
            }

            // Configurar listener para el botón mapa
            val mapaButton = findViewById<Button>(R.id.mapa)
            mapaButton.text= getString(R.string.hitza_osatu)
            mapaButton.setOnClickListener {
                val nombre=intent.getStringExtra("nombre").toString()
                val apellido=intent.getStringExtra("apellido").toString()
                val intent = Intent(this, OrdenarPalabra::class.java)
                intent.putExtra("nombre",nombre)
                intent.putExtra("apellido",apellido)
                startActivity(intent)
                finish()
            }

            // Configurar el ícono de ayuda
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
                    getString(R.string.helburu_finalerako_letra_bat_lortu_duzu_itzuli_mapa_atzera_nahi_baduzu_sakatu_mapa_botoia)
                // Configurar el botón "cerrar" para cerrar la ventana emergente
                val cerrar: Button = ayudaView.findViewById(R.id.cerrar)
                cerrar.setOnClickListener {
                    ayudaDialog.dismiss() // Cierra solo la ventana emergente
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.txarto_egin_duzu_saiatu_berriro), Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * Reemplaza un TextView con un EditText en el diseño.
     *
     * @author Aketza
     * @param textView el TextView que se reemplazará.
     * @param hint el texto de sugerencia para el EditText.
     */
    private fun replaceTextViewWithEditText(textView: TextView, hint: String) {
        val parent = textView.parent as ConstraintLayout
        val editText = EditText(this)
        editText.id = textView.id
        editText.layoutParams = textView.layoutParams
        editText.hint = hint

        parent.removeView(textView)
        parent.addView(editText)
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
                getString(R.string.abestia_testua_ondo_irakurri_eta_ondoren_agertzen_diren_hitz_desordenatuak_irakurri_ondoren_hasi_botoian_klik_egin_dezakezu_hutsuneak_hitz_egokiekin_betetzeko)
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
