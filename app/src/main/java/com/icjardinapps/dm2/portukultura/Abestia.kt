package com.icjardinapps.dm2.portukultura
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
/**
 * Actividad principal para gestionar la edición y validación de textos en un entorno interactivo.
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
    }
    /**
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
     * Muestra un mensaje de éxito si los textos son correctos; de lo contrario, muestra un mensaje de error.
     */
    private fun validateInputs() {
        val subeArribaInput = findViewById<EditText>(pantorrilla.id).text.toString()
        val queMeMareoInput = findViewById<EditText>(chiquitin.id).text.toString()
        val pantorrillaInput = findViewById<EditText>(subeArriba.id).text.toString()
        val chiquitinInput = findViewById<EditText>(queMeMareo.id).text.toString()

        if (subeArribaInput.trim().lowercase().equals("sube arriba") && queMeMareoInput.trim().lowercase().equals("que me mareo") &&
            pantorrillaInput.trim().lowercase().equals("pantorrilla") && chiquitinInput.trim().lowercase().equals("chiquitin")) {

            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, OrdenarPalabra::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Error en los textos, inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * Reemplaza un TextView con un EditText en el diseño.
     *
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
}
