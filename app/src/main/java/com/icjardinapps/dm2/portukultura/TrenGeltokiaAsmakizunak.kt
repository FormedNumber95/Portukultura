package com.icjardinapps.dm2.portukultura

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class TrenGeltokiaAsmakizunak : AppCompatActivity() {
    private lateinit var asmakizuna1: TextView
    private lateinit var asmakizuna2: TextView
    private lateinit var asmakizuna3: TextView
    private lateinit var asmakizuna4: TextView
    private lateinit var objeto1: TextView
    private lateinit var objeto2: TextView
    private lateinit var objeto3: TextView
    private lateinit var objeto4: TextView
    private lateinit var mainLayout: ConstraintLayout
    private var remainingPairs = 4

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

    private fun setDraggable(view: TextView) {
        view.setOnTouchListener { v, event ->
            val clipData = ClipData.newPlainText("", "")
            val shadow = View.DragShadowBuilder(v)
            v.startDragAndDrop(clipData, shadow, v, 0)
            true
        }
    }
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

    private fun checkCompletion() {
        if (remainingPairs == 0) {
            // Si no quedan pares, cerramos la actividad
            finish()
        }
    }

}