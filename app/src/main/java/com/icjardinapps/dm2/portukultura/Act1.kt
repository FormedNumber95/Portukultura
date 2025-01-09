package com.icjardinapps.dm2.portukultura

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.ImageView

/**
     * Sets up drag-and-drop listeners for game elements.
     */

class Act1 : AppCompatActivity() {

    // References to images and text
    private lateinit var Imagen1: ImageView
    private lateinit var Imagen2: ImageView
    private lateinit var Imagen3: ImageView
    private lateinit var Imagen4: ImageView
    private lateinit var Imagen5: ImageView
    private lateinit var Texto1: TextView
    private lateinit var Texto2: TextView
    private lateinit var Texto3: TextView
    private lateinit var Texto4: TextView
    private lateinit var Texto5: TextView

    /**
     * Contenedor principal de la actividad.
     */
    private lateinit var mainLayout: ConstraintLayout

    /**
     * Número de pares restantes para completar el juego.
     */
    private var remainingPairs = 5

    /**
     * Configura la actividad al crearse.
     *
     * @param savedInstanceState estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act1)
        Imagen1 = findViewById(R.id.imagen1)
        Imagen2 = findViewById(R.id.imagen2)
        Imagen3 = findViewById(R.id.imagen3)
        Imagen4 = findViewById(R.id.imagen4)
        Imagen5 = findViewById(R.id.imagen5)
        Texto1 = findViewById(R.id.texto1)
        Texto2 = findViewById(R.id.texto2)
        Texto3 = findViewById(R.id.texto3)
        Texto4 = findViewById(R.id.texto4)
        Texto5 = findViewById(R.id.texto5)
        mainLayout = findViewById(R.id.main)

        // Configuramos el arrastre
        setDraggable(Imagen1)
        setDraggable(Imagen2)
        setDraggable(Imagen3)
        setDraggable(Imagen4)
        setDraggable(Imagen5)

        // Configuramos los receptores
        setDroppable(Texto5, Imagen1)
        setDroppable(Texto2, Imagen2)
        setDroppable(Texto3, Imagen3)
        setDroppable(Texto1, Imagen4)
        setDroppable(Texto4, Imagen5)
    }

    /**
     * Hace que un TextView sea arrastrable.
     *
     * @param view la vista que será arrastrable.
     */
    private fun setDraggable(view: ImageView) {
        view.setOnTouchListener { v, event ->
            val clipData = ClipData.newPlainText("", "")
            val shadow = View.DragShadowBuilder(v)
            v.startDragAndDrop(clipData, shadow, v, 0)
            true
        }
    }

    private fun setDroppable(target: TextView, matchingView: ImageView) {
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
            finish()
        }
    }

}