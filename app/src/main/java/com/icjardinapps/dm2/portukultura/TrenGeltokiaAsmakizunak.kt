package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.system.exitProcess

/**
 * Actividad principal que implementa un juego de emparejar.
 *
 * @author Aketza
 * @version 1.2
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
     * @author Aketza
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
        configurarAyuda()
    }
    /**
     * Hace que un TextView sea arrastrable.
     *
     * @author Aketza
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
     * @author Aketza
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
     *
     * @author Aketza, Intissar
     */
    private fun checkCompletion() {
        if (remainingPairs == 0) {
            // Si no quedan pares, cerramos la actividad

            setContentView(R.layout.letra) // Cambiar al diseño letra.xml

            // Modificar el texto de textLetra si está vacío
            val textLetra = findViewById<TextView>(R.id.textLetra)
            if (textLetra.text.isEmpty()) {
                textLetra.text = getString(R.string.d_lortu_duzue)
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
                getString(R.string.joko_honek_testua_dagokion_esaldira_arrastatzea_du_helburu)
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