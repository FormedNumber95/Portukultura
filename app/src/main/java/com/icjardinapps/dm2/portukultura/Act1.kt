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
import android.widget.ImageView

/**
 * Clase que representa la actividad principal del juego de emparejar imágenes con conceptos.
 * Configura los elementos interactivos y maneja la lógica de arrastrar y soltar.
 *
 * @author Diego
 */
class Act1 : AppCompatActivity() {

    // Referencias a las imágenes y los textos
    private lateinit var fuente: ImageView
    private lateinit var palmeras: ImageView
    private lateinit var buho: ImageView
    private lateinit var monos: ImageView
    private lateinit var jardin: ImageView
    private lateinit var tximinoak: TextView
    private lateinit var palmondoak: TextView
    private lateinit var hontza: TextView
    private lateinit var etxea: TextView
    private lateinit var iturria: TextView

    /**
     * Contenedor principal de la actividad.
     *
     * @author Diego
     */
    private lateinit var mainLayout: ConstraintLayout

    /**
     * Número de pares restantes para completar el juego.
     *
     * @author Diego
     */
    private var remainingPairs = 5

    /**
     * Inicializa la actividad y configura los elementos interactivos.
     *
     * @author Diego
     * @param savedInstanceState estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act1)
        fuente = findViewById(R.id.fuente)
        palmeras = findViewById(R.id.palmeras)
        buho = findViewById(R.id.buho)
        monos = findViewById(R.id.monos)
        jardin = findViewById(R.id.jardin)
        tximinoak = findViewById(R.id.tximinoak)
        palmondoak = findViewById(R.id.palmondoak)
        hontza = findViewById(R.id.hontza)
        etxea = findViewById(R.id.etxea)
        iturria = findViewById(R.id.iturria)
        mainLayout = findViewById(R.id.main)

        // Configuramos el arrastre
        setDraggable(fuente)
        setDraggable(palmeras)
        setDraggable(buho)
        setDraggable(monos)
        setDraggable(jardin)

        // Configuramos los receptores
        setDroppable(iturria, fuente)
        setDroppable(palmondoak, palmeras)
        setDroppable(hontza, buho)
        setDroppable(tximinoak, monos)
        setDroppable(etxea, jardin)
        configurarAyuda()
    }

    /**
     * Hace que una imagen sea arrastrable.
     *
     * @author Diego
     * @param view la vista de la imagen que será arrastrable.
     */
    private fun setDraggable(view: ImageView) {
        view.setOnTouchListener { v, event ->
            val clipData = ClipData.newPlainText("", "")
            val shadow = View.DragShadowBuilder(v)
            v.startDragAndDrop(clipData, shadow, v, 0)
            true
        }
    }

    /**
     * Configura un área de destino para aceptar elementos arrastrados.
     *
     * @author Diego
     * @param target la vista de texto que aceptará el elemento arrastrado.
     * @param matchingView la vista de la imagen que debe ser arrastrada y coincidir.
     */
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
     * Si no quedan pares, finaliza la actividad.
     *
     * @author Diego
     */
    private fun checkCompletion() {
        if (remainingPairs == 0) {
            setContentView(R.layout.letra) // Cambiar al diseño letra.xml

            // Modificar el texto de textLetra si está vacío
            setContentView(R.layout.letra) // Cambiar al diseño letra.xml

            // Modificar el texto de textLetra si está vacío
            val textLetra = findViewById<TextView>(R.id.textLetra)
            if (textLetra.text.isEmpty()) {
                textLetra.text = "O LORTU DUZUE"
            }

            // Configurar listener para el botón mapa
            val mapaButton = findViewById<Button>(R.id.mapa)
            mapaButton.setOnClickListener {
                finish() // Cierra la actividad
            }
        }
    }
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
            textoAyuda.text = "Abestia: Testua ondo irakurri eta ondoren agertzen diren hitz desordenatuak. Irakurri ondoren, 'Hasi' botoian klik egin dezakezu hutsuneak hitz egokiekin betetzeko."
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
    override fun onBackPressed() {}
}
