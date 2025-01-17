package com.icjardinapps.dm2.portukultura

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrdenarPalabra : AppCompatActivity() {

    private val letras:Array<Char> = arrayOf('O','N','D','A','R','E','A')
    private var remainingPairs = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ordenar_palabra)
        //Las letras para agarrar
        val letra1:TextView=findViewById(R.id.letra1)
        val letra2:TextView=findViewById(R.id.letra2)
        val letra3:TextView=findViewById(R.id.letra3)
        val letra4:TextView=findViewById(R.id.letra4)
        val letra5:TextView=findViewById(R.id.letra5)
        val letra6:TextView=findViewById(R.id.letra6)
        val letra7:TextView=findViewById(R.id.letra7)
        //La posicion donde va cada letra
        val primeraLetra:TextView=findViewById(R.id.primeraLetra)
        val segundaLetra:TextView=findViewById(R.id.segundaLetra)
        val terceraLetra:TextView=findViewById(R.id.terceraLetra)
        val cuartaLetra:TextView=findViewById(R.id.cuartaLetra)
        val quintaLetra:TextView=findViewById(R.id.quintaLetra)
        val sextaLetra:TextView=findViewById(R.id.sextaLetra)
        val septimaLetra:TextView=findViewById(R.id.septimaLetra)
        //Asignar las letras con una letra aleatoria
        letras.shuffle()
        letra1.text=letras[0].toString()
        letra2.text=letras[1].toString()
        letra3.text=letras[2].toString()
        letra4.text=letras[3].toString()
        letra5.text=letras[4].toString()
        letra6.text=letras[5].toString()
        letra7.text=letras[6].toString()
        //hacer que sea arrastrable
        setDraggable(letra1)
        setDraggable(letra2)
        setDraggable(letra3)
        setDraggable(letra4)
        setDraggable(letra5)
        setDraggable(letra6)
        setDraggable(letra7)
        //hacer el drop
        setDroppable(primeraLetra,"O")
        setDroppable(segundaLetra,"N")
        setDroppable(terceraLetra,"D")
        setDroppable(cuartaLetra,"A")
        setDroppable(quintaLetra,"R")
        setDroppable(sextaLetra,"E")
        setDroppable(septimaLetra,"A")
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

    private fun setDroppable(target: TextView, matchingText: String) {
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
                    val draggedView = event.localState as? TextView
                    if (draggedView?.text == matchingText) {
                        // Si el texto coincide, ocultamos ambas vistas
                        (v as TextView).text=draggedView.text
                        draggedView.text=""
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
            val intent = Intent(this, QR::class.java)
            startActivity(intent)
            finish()
        }
    }

}