package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
/**
 * Actividad principal que implementa un juego de emparejar.
 */
class TrenGeltokiaAsmakizunak : AppCompatActivity() {
    /**
     * Referencias a los elementos de asmakizunak (adivinanzas).
     */
    private lateinit var asmakizuna1: TextView
    private lateinit var asmakizuna2: TextView
    private lateinit var asmakizuna3: TextView
    private lateinit var asmakizuna4: TextView
    /**
     * Referencias a los elementos de objetos donde se pueden soltar las adivinanzas.
     */
    private lateinit var objeto1: TextView
    private lateinit var objeto2: TextView
    private lateinit var objeto3: TextView
    private lateinit var objeto4: TextView
    /**
     * Contenedor principal de la actividad.
     */
    private lateinit var mainLayout: ConstraintLayout
    /**
     * Número de pares restantes para completar el juego.
     */
    private var remainingPairs = 4
    /**
     * Configura la actividad al crearse.
     *
     * @param savedInstanceState estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tren_geltokia_asmakizunak)
        asmakizuna1 = findViewById(R.id.Asmakizuna1)
        asmakizuna2 = findViewById(R.id.Asmakizuna2)
        asmakizuna3 = findViewById(R.id.Asmakizuna3)
        asmakizuna4 = findViewById(R.id.Asmakizuna4)
        objeto1 = findViewById(R.id.objeto1)
        objeto2 = findViewById(R.id.objeto2)
        objeto3 = findViewById(R.id.objeto3)
        objeto4 = findViewById(R.id.objeto4)
        mainLayout = findViewById(R.id.main)

        // Configuramos el arrastre
        setDraggable(asmakizuna1)
        setDraggable(asmakizuna2)
        setDraggable(asmakizuna3)
        setDraggable(asmakizuna4)

        // Configuramos los receptores
        setDroppable(objeto1, asmakizuna4)
        setDroppable(objeto2, asmakizuna2)
        setDroppable(objeto3, asmakizuna1)
        setDroppable(objeto4, asmakizuna3)
    }
    /**
     * Hace que un TextView sea arrastrable.
     *
     * @param view la vista que será arrastrable.
     */
    private fun setDraggable(view: TextView) {
        view.setOnTouchListener { v, event ->
            val clipData = ClipData.newPlainText("", "")
            val shadow = View.DragShadowBuilder(v)
            v.startDragAndDrop(clipData, shadow, v, 0)
            true
        }
    }
    /**
     * Hace que un TextView sea un receptor de arrastre y lo empareja con una vista específica.
     *
     * @param target        el TextView que actúa como receptor de arrastre.
     * @param matchingView  la vista que debe emparejarse con el receptor.
     */
    private fun setDroppable(target: TextView, matchingView: TextView) {
        target.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> true
                DragEvent.ACTION_DRAG_ENTERED -> {
                    v.alpha = 0.5f // Indicar que se puede soltar
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    v.alpha = 1.0f // Restaurar opacidad
                    true
                }
                DragEvent.ACTION_DROP -> {
                    v.alpha = 1.0f
                    val draggedView = event.localState as? View
                    if (draggedView == matchingView) {
                        // Si coincide, ocultamos ambas vistas
                        draggedView?.visibility = View.GONE
                        v.visibility = View.GONE
                        remainingPairs--
                        checkCompletion()
                    }
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    v.alpha = 1.0f
                    true
                }
                else -> false
            }
        }
    }
    /**
     * Verifica si todos los pares han sido emparejados correctamente.
     * Si es así, cierra la actividad.
     */
    private fun checkCompletion() {
        if (remainingPairs == 0) {
            // Si no quedan pares, cerramos la actividad

            setContentView(R.layout.letra) // Cambiar al diseño letra.xml

            // Modificar el texto de textLetra si está vacío
            val textLetra = findViewById<TextView>(R.id.textLetra)
            if (textLetra.text.isEmpty()) {
                textLetra.text = "D LORTU DUZUE"
            }

            // Configurar listener para el botón mapa
            val mapaButton = findViewById<Button>(R.id.mapa)
            mapaButton.setOnClickListener {
                finish() // Cierra la actividad
            }

        }
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }
}