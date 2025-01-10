package com.icjardinapps.dm2.portukultura

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout

class SopaDeLetras : AppCompatActivity() {

    // La sopa de letras
    private val sopaDeLetras: Array<Array<Char>> = arrayOf(
        arrayOf('C', 'T', 'J', 'Z', 'Y', 'O', 'U', 'E', 'U', 'P', 'W'),
        arrayOf('D', 'I', 'A', 'D', 'C', 'S', 'A', 'W', 'R', 'O', 'D'),
        arrayOf('R', 'U', 'R', 'S', 'H', 'P', 'B', 'D', 'B', 'R', 'R'),
        arrayOf('J', 'G', 'R', 'Z', 'A', 'E', 'B', 'W', 'Z', 'T', 'T'),
        arrayOf('S', 'R', 'I', 'M', 'W', 'T', 'H', 'B', 'U', 'U', 'F'),
        arrayOf('O', 'F', 'L', 'V', 'X', 'S', 'Q', 'O', 'S', 'G', 'A'),
        arrayOf('N', 'I', 'L', 'V', 'K', 'U', 'J', 'Y', 'Y', 'A', 'R'),
        arrayOf('E', 'V', 'E', 'H', 'W', 'A', 'P', 'X', 'K', 'L', 'D'),
        arrayOf('N', 'I', 'R', 'Y', 'X', 'V', 'E', 'N', 'E', 'E', 'O'),
        arrayOf('A', 'S', 'O', 'V', 'I', 'S', 'D', 'S', 'T', 'T', 'A'),
        arrayOf('J', 'J', 'L', 'L', 'A', 'A', 'O', 'L', 'V', 'E', 'Z')
    )

    // La lista de palabras a buscar
    private val palabras: MutableList<String> = mutableListOf("onena", "jarrillero", "ospetsua", "portugalete", "ardoa")

    // Lista de TextViews seleccionados
    private val seleccionados: MutableList<TextView> = mutableListOf()

    private lateinit var grid: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sopa_de_letras)
        cargarSopaDeLetras()
    }

    private fun cargarSopaDeLetras() {
        grid = findViewById(R.id.gridSopaLetras)
        for (fila: Array<Char> in sopaDeLetras) {
            for (letra: Char in fila) {
                val textView: TextView = TextView(this).apply {
                    text = letra.toString()
                    textSize = 18f
                    setTextColor(Color.BLACK)
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    setBackgroundColor(Color.WHITE)
                    isEnabled = true
                }

                val params: GridLayout.LayoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                textView.layoutParams = params
                grid.addView(textView)
            }
        }

        grid.setOnTouchListener { _, event -> manejarTouch(event) }
    }

    private fun manejarTouch(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                seleccionados.clear()
                detectarLetra(event)
            }
            MotionEvent.ACTION_MOVE -> {
                detectarLetra(event)
            }
            MotionEvent.ACTION_UP -> {
                verificarPalabra()
            }
        }
        return true
    }

    private fun detectarLetra(event: MotionEvent) {
        val x = event.rawX
        val y = event.rawY

        for (i in 0 until grid.childCount) {
            val child = grid.getChildAt(i)
            if (child is TextView && child.isEnabled) {
                val location = IntArray(2)
                child.getLocationOnScreen(location)
                val left = location[0]
                val top = location[1]
                val right = left + child.width
                val bottom = top + child.height

                if (x >= left && x <= right && y >= top && y <= bottom && !seleccionados.contains(child)) {
                    seleccionarLetra(child)
                }
            }
        }
    }

    private fun seleccionarLetra(view: TextView) {
        view.setBackgroundColor(Color.YELLOW)
        seleccionados.add(view)
    }

    private fun verificarPalabra() {
        val palabraSeleccionada = seleccionados.joinToString("") { it.text.toString() }.lowercase()

        if (palabras.contains(palabraSeleccionada)) {
            // Palabra encontrada
            seleccionados.forEach {
                it.setBackgroundColor(Color.GREEN)
                it.isEnabled = false
            }
            palabras.remove(palabraSeleccionada)
            when (palabraSeleccionada) {
                "onena" -> tacharPalabra(findViewById(R.id.onena))
                "jarrillero" -> tacharPalabra(findViewById(R.id.jarrillero))
                "ospetsua" -> tacharPalabra(findViewById(R.id.ospetsua))
                "portugalete" -> tacharPalabra(findViewById(R.id.portugalete))
                "ardoa" -> tacharPalabra(findViewById(R.id.ardoa))
            }
            if (palabras.isEmpty()) {
                // Todas las palabras encontradas, volver a la actividad anterior
                //finish()
                setContentView(R.layout.letra) // Cambiar al diseño letra.xml

                // Modificar el texto de textLetra si está vacío
                val textLetra = findViewById<TextView>(R.id.textLetra)
                if (textLetra.text.isEmpty()) {
                    textLetra.text = "A LORTU DUZUE"
                }

                // Configurar listener para el botón mapa
                val mapaButton = findViewById<Button>(R.id.mapa)
                mapaButton.setOnClickListener {
                    finish() // Cierra la actividad
                }

            }
        } else {
            // Si no coincide, limpiar selección
            seleccionados.forEach { it.setBackgroundColor(Color.WHITE) }
        }
        seleccionados.clear()
    }

    private fun tacharPalabra(texto: TextView) {
        texto.paintFlags = texto.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}