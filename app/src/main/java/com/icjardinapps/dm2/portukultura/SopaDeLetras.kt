package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.gridlayout.widget.GridLayout
import kotlin.math.min
import kotlin.math.abs


/**
 * Esta clase representa la actividad de una sopa de letras en una aplicación Android.
 * Permite a los usuarios interactuar con una sopa de letras para buscar palabras específicas
 * y realiza diversas acciones según las palabras encontradas.
 *
 * @author Aketza
 * @version 1.1
 */
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

    private var inicial: Pair<Int, Int>? = null

    /**
     * Metodo de inicialización que configura la actividad y carga la sopa de letras.
     *
     * @author Aketza
     * @param savedInstanceState estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sopa_de_letras)
        cargarSopaDeLetras()
        // Configurar la funcionalidad de ayuda
        configurarAyuda()

    }

    /**
     * Obtiene una secuencia de vistas dentro de una cuadrícula, basándose en dos puntos de inicio y fin.
     *
     * Este método devuelve una secuencia de celdas (vistas) dentro de un GridPane, seleccionando
     * las celdas en una línea vertical u horizontal entre los puntos dados. Si los puntos no están
     * alineados en una de estas direcciones, se devuelve una secuencia vacía.
     *
     * @param start Coordenadas de la celda inicial como un par (columna, fila).
     * @param end Coordenadas de la celda final como un par (columna, fila).
     * @return Una secuencia de vistas que representa las celdas en la dirección especificada.
     *         Devuelve una secuencia vacía si los puntos no forman una línea recta.
     *
     * @author Aketza
     */
    fun getCeldas(start: Pair<Int, Int>, end: Pair<Int, Int>): Sequence<View> {
        val x = end.first - start.first
        val y = end.second - start.second
        if (x == 0) {
            val minY = min(start.second, end.second)
            return grid.children
                .chunked(grid.columnCount)
                .map { it[start.first] }
                .drop(minY)
                .take(abs(y) + 1)
        }
        if (y == 0) {
            val minX = min(start.first, end.first)
            return grid.children
                .drop(minX + grid.columnCount * start.second)
                .take(abs(x) + 1)
        }
        return emptySequence()
    }

    /**
     * Carga la sopa de letras en el GridLayout y configura el listener de interacción táctil.
     *
     * @author Aketza
     */
    private fun cargarSopaDeLetras() {
        grid = findViewById(R.id.gridSopaLetras)
        for (rowIndex in sopaDeLetras.indices) {
            for (colIndex in sopaDeLetras[rowIndex].indices) {
                val letra = sopaDeLetras[rowIndex][colIndex]
                val textView: TextView = TextView(this).apply {
                    text = letra.toString()
                    textSize = 18f
                    setTextColor(Color.BLACK)
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    setBackgroundColor(Color.WHITE)
                    isEnabled = true
                    tag = Pair(colIndex, rowIndex)
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

    /**
     * Maneja los eventos táctiles para la interacción con la sopa de letras.
     *
     * @author Aketza
     * @param event el evento táctil detectado.
     * @return true si se procesa el evento, false en caso contrario.
     */
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
                inicial=null
            }
        }
        return true
    }

    /**
     * Detecta la letra seleccionada durante la interacción táctil.
     *
     * @author Aketza
     * @param event el evento táctil detectado.
     */
    private fun detectarLetra(event: MotionEvent) {
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        for (i in 0 until grid.childCount) {
            val child = grid.getChildAt(i)
            if (child is TextView && child.isEnabled) {
                var pairEnviar=child.tag as Pair<Int, Int>?
                val location = IntArray(2)
                child.getLocationOnScreen(location)
                val left = location[0]
                val top = location[1]
                val right = left + child.width
                val bottom = top + child.height

                if (x >= left && x <= right && y >= top && y <= bottom && !seleccionados.contains(child)) {
                    if(inicial==null){
                        inicial= child.tag as Pair<Int, Int>?
                    }
                    if (pairEnviar != null) {
                        seleccionarLetra(child,pairEnviar)
                    }
                }
            }
        }
    }

    /**
     * Marca un TextView como seleccionado y lo agrega a la lista de seleccionados.
     *
     * @author Aketza
     * @param view el TextView seleccionado.
     */
    private fun seleccionarLetra(view: TextView,pair:Pair<Int,Int>) {
        seleccionados.forEach { it.setBackgroundColor(Color.WHITE)}
        getCeldas(inicial!!, pair).forEach { it.setBackgroundColor(Color.YELLOW) }
        seleccionados.clear()
        seleccionados.addAll(getCeldas(inicial!!, pair) as Sequence<TextView>)
    }

    /**
     * Verifica si la palabra formada por las letras seleccionadas está en la lista de palabras.
     * Si la palabra es válida, la elimina de la lista y realiza las acciones correspondientes.
     *
     * @author Aketza, Intissar
     */
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
                    textLetra.text = getString(R.string.a_lortu_duzue)
                }

                // Configurar listener para el botón mapa
                val mapaButton = findViewById<Button>(R.id.mapa)
                mapaButton.setOnClickListener {
                    finish() // Cierra la actividad
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

            }
        } else {
            // Si no coincide, limpiar selección
            seleccionados.forEach { it.setBackgroundColor(Color.WHITE) }
        }
        seleccionados.clear()
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
                getString(R.string.hitz_zopa_aurkitu_hitz_zopan_dauden_hitzak_aurkitzen_duzun_bakoitzean_marraztuta_agertuko_da)
            // Configurar el botón "cerrar" para cerrar la ventana emergente
            val cerrar: Button = ayudaView.findViewById(R.id.cerrar)
            cerrar.setOnClickListener {
                ayudaDialog.dismiss() // Cierra solo la ventana emergente
            }
        }
    }


    /**
     * Tacha una palabra encontrada en la lista de palabras mostrada al usuario.
     *
     * @author Aketza
     * @param texto el TextView correspondiente a la palabra.
     */
    private fun tacharPalabra(texto: TextView) {
        texto.paintFlags = texto.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    /**
     * Funcion vacia que elimina el uso del boton  de retroceso
     * @author Intissar
     */
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }
}