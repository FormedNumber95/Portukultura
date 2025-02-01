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
import kotlin.system.exitProcess

/**
 * Clase que implementa una actividad de juego basada en arrastrar y soltar.
 * Configura los elementos interactivos y la lógica del juego.
 *
 * @author Diego
 * @version 1.2
 */
class Act6DnD : AppCompatActivity() {

    // Referencias a las imágenes y textos
    private lateinit var pan: ImageView
    private lateinit var terraza: ImageView
    private lateinit var equipo: ImageView
    private lateinit var casa: ImageView
    private lateinit var arrautz: TextView
    private lateinit var canillako: TextView
    private lateinit var jabeak: TextView
    private lateinit var santa_maria: TextView

    /**
     * Número de pares restantes para completar el juego.
     *
     * @author Diego
     */
    private var remainingPairs = 4

    /**
     * Inicializa la actividad y configura los elementos interactivos.
     *
     * @author Diego
     * @param savedInstanceState estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act6_dnd)

        pan = findViewById(R.id.pan)
        terraza = findViewById(R.id.terraza)
        equipo = findViewById(R.id.equipo)
        casa = findViewById(R.id.casa)
        arrautz = findViewById(R.id.arrautz_ospetsu_hau_pilota_forma_du_eta_ogiarekin_zerbitzatzen_da)
        canillako = findViewById(R.id.taberna_honen_aurrean_canillako_tren_geltokia_dago)
        jabeak = findViewById(R.id.taberna_honen_jabeak_beraien_bezeroak_oso_ondo_zaintzeaz_ospea_dute)
        santa_maria = findViewById(R.id.taberna_hau_santa_maria_basilikaren_beheko_aldean_kokatuta_dago)

        // Configurar drag-and-drop
        setDraggable(pan)
        setDraggable(terraza)
        setDraggable(equipo)
        setDraggable(casa)

        setDroppable(santa_maria, pan)
        setDroppable(canillako, terraza)
        setDroppable(jabeak, equipo)
        setDroppable(arrautz, casa)
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
     * @param matchingView la vista de la imagen que debe coincidir al ser arrastrada.
     */
    private fun setDroppable(target: TextView, matchingView: ImageView) {
        target.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> true
                DragEvent.ACTION_DRAG_ENTERED -> {
                    v.alpha = 0.5f
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    v.alpha = 1.0f
                    true
                }
                DragEvent.ACTION_DROP -> {
                    v.alpha = 1.0f
                    val draggedView = event.localState as? View
                    if (draggedView == matchingView) {
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
     * @author Intissar
     */
    private fun checkCompletion() {
        if (remainingPairs == 0) {
            setContentView(R.layout.letra) // Cambiar al diseño letra.xml

            // Modificar el texto de textLetra si está vacío
            val textLetra = findViewById<TextView>(R.id.textLetra)
            if (textLetra.text.isEmpty()) {
                textLetra.text = getString(R.string.e_lortu_duzue)
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
                getString(R.string.irakurri_testua_eta_klikatu_hurrengo_botoian_jarraitzeko)
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

