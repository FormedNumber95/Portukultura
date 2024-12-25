package com.icjardinapps.dm2.portukultura

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
/**
 * Actividad principal para gestionar un cuestionario con preguntas.
 */
class TrenGeltokiaGalderaErrepikagarriak : AppCompatActivity() {
    /**
     * Configura la actividad al crearse.
     *
     * @param savedInstanceState estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tren_geltokia_galdera_errepikagarriak)

        val btnAurrera = findViewById<Button>(R.id.btnAurrera)
        val radioGroupPregunta1 = findViewById<RadioGroup>(R.id.radioGroupPregunta1)
        val radioGroupPregunta2 = findViewById<RadioGroup>(R.id.radioGroupPregunta2)
        val radioGroupPregunta3 = findViewById<RadioGroup>(R.id.radioGroupPregunta3)

        btnAurrera.setOnClickListener {
            // Obtener el ID del RadioButton seleccionado en cada RadioGroup
            val selectedOption1 = radioGroupPregunta1.checkedRadioButtonId
            val selectedOption2 = radioGroupPregunta2.checkedRadioButtonId
            val selectedOption3 = radioGroupPregunta3.checkedRadioButtonId

            // Verificar si los IDs seleccionados coinciden con los correctos
            val isCorrect = selectedOption1 == R.id.rad1888 &&
                    selectedOption2 == R.id.radAldeZaharrean &&
                    selectedOption3 == R.id.radBai

            if (isCorrect) {
                val intent = Intent(this, TrenGeltokiaAsmakizunak::class.java)
                startActivity(intent)
                // Cierra esta actividad para evitar volver a ella
                finish()
            } else {
                Toast.makeText(this, "Algunas respuestas son incorrectas.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
