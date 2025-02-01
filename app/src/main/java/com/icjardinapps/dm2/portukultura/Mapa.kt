package com.icjardinapps.dm2.portukultura

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.system.exitProcess
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.icjardinapps.dm2.portukultura.databinding.MapaBinding

/**
 * Actividad que muestra un mapa interactivo con marcadores. Cada marcador está asociado a una actividad que se
 * lanza al hacer clic sobre un marcador de color amarillo.
 * Los marcadores cambian de color para indicar el marcador activo.
 *
 * @author Aketza
 * @version 1.3
 */
class Mapa : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var nombre:String
    private lateinit var apellido:String

    private lateinit var mMap: GoogleMap
    private lateinit var binding: MapaBinding
    /**
     * Mapa de marcadores y su color asociado.
     */
    private val markerColors = HashMap<Marker, Float>()

    /**
     * Lista ordenada de los marcadores en el mapa.
     */
    private var markersList: MutableList<Marker> = mutableListOf()

    /**
     * Índice del marcador activo, usado para cambiar de marcador.
     */
    private var activeMarkerIndex = 0

    /**
     * Mapa de actividades asociadas a cada marcador.
     */
    private lateinit var markerActivities: Map<Marker, Class<out AppCompatActivity>>
    /**
     * Metodo llamado al crear la actividad.
     * Inicializa el mapa y configura el fragmento del mapa.
     *
     *@author Aketza
     * @param savedInstanceState Estado guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        nombre= intent.getStringExtra("nombre").toString()
        apellido= intent.getStringExtra("apellido").toString()
        configurarAyuda()
        val txtNombre:TextView=findViewById(R.id.textViewTop)
        txtNombre.text=nombre+" "+apellido
    }
    /**
     * Metodo llamado cuando el mapa está listo para ser utilizado.
     * Configura los marcadores en el mapa y establece la lógica para interactuar con ellos.
     *
     * @author Aketza
     * @param googleMap El mapa de Google.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Coordenadas de los marcadores
        val markerLocations = listOf(
            LatLng(43.32499609828457, -3.020763949816549) to "Areilza",
            LatLng(43.31871964219722, -3.013891264960716) to "Rialia",
            LatLng(43.32327983515113, -3.0171740054884357) to "Zubia",
            LatLng(43.321473128061704, -3.0171637139280474) to "Jarrilla",
            LatLng(43.32027889068112, -3.015688498966755) to "Arrautza",
            LatLng(43.31860888436704, -3.015193226072288) to "Tren",
            LatLng(43.32065891272269, -3.0178038091884094) to "LaGia"
        )

        // Crear y configurar marcadores
        markersList = markerLocations.mapIndexed { index, (location, title) ->
            val hue = if (index == 0) BitmapDescriptorFactory.HUE_YELLOW else BitmapDescriptorFactory.HUE_RED
            val marker = mMap.addMarker(
                MarkerOptions().position(location).title(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(hue))
            )!!
            markerColors[marker] = hue
            marker
        }.toMutableList()

        // Mapa de actividades para los marcadores
        markerActivities = markersList.mapIndexed { index, marker ->
            val activity = when (index) {
                0 -> Video::class.java
                1 -> Video::class.java
                2 -> Video::class.java
                3 -> SopaDeLetras::class.java
                4 -> Video::class.java
                5 -> Act6Imagen::class.java
                6 -> Video::class.java

                //Nunca se va a dar este caso
                else -> Class.forName("com.icjardinapps.dm2.portukultura.Ejemplo${index}") as Class<out AppCompatActivity>
            }
            marker to activity
        }.toMap()


        // Mover la cámara al primer marcador
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocations[0].first, 15f))

        // Configurar el evento onClick para los marcadores
        mMap.setOnMarkerClickListener { marker ->
            val markerHue = markerColors[marker]

            if (markerHue == BitmapDescriptorFactory.HUE_YELLOW) {
                // Lanzar actividad si el marcador es amarillo
                val activityClass = markerActivities[marker]
                if (activityClass != null) {
                    val markerIndex = markersList.indexOf(marker)
                    val intent = Intent(this, activityClass).apply {
                        putExtra("MARKER_INDEX", markerIndex) // Enviar el índice del marcador
                        if(markerIndex==6) {
                            putExtra("nombre", nombre)
                            putExtra("apellido", apellido)
                        }
                    }
                    startActivity(intent)
                }
                true
            } else {
                // No hacer nada si el marcador no es amarillo
                false
            }
        }

        // Configurar para evitar mostrar los títulos de los marcadores no amarillos
        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): android.view.View? {
                return if (markerColors[marker] == BitmapDescriptorFactory.HUE_YELLOW) null
                else android.view.View(this@Mapa)
            }

            override fun getInfoContents(marker: Marker): android.view.View? {
                return if (markerColors[marker] == BitmapDescriptorFactory.HUE_YELLOW) null
                else android.view.View(this@Mapa)
            }
        })
    }
    /**
     * Metodo llamado cuando la actividad vuelve a estar en primer plano.
     * Actualiza el color del marcador activo y cambia al siguiente marcador.
     *
     * @author Aketza
     */
    override fun onResume() {
        super.onResume()

        // Verificar si la lista ya está cargada antes de modificar los marcadores
        if (markersList.isNotEmpty()) {
            // Cambiar el color del marcador activo a verde
            val activeMarker = markersList[activeMarkerIndex]
            activeMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            markerColors[activeMarker] = BitmapDescriptorFactory.HUE_GREEN

            // Mover al siguiente marcador en la lista
            activeMarkerIndex = (activeMarkerIndex + 1) % markersList.size
            val nextMarker = markersList[activeMarkerIndex]
            nextMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
            markerColors[nextMarker] = BitmapDescriptorFactory.HUE_YELLOW
        }
    }
    /**
     * Metodo para guardar el estado de la actividad antes de que sea destruida.
     * Guarda el índice del marcador activo.
     *
     * @author Aketza
     * @param outState Estado guardado.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("activeMarkerIndex", activeMarkerIndex)
    }
    /**
     * Metodo para restaurar el estado de la actividad después de que haya sido destruida.
     * Restaura el índice del marcador activo.
     *
     * @author Aketza
     * @param savedInstanceState Estado guardado.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        activeMarkerIndex = savedInstanceState.getInt("activeMarkerIndex", 0)
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
                getString(R.string.mapa_behatu_jarduerak_egiteko_puntuaren_kolorea_kontuan_hartu_behar_duzu_horia_kolorea_duen_puntu_batek_adierazten_du_jarduera_jolastera_prest_dagoela_berde_kolorea_duten_puntuek_esan_nahi_dute_dagoeneko_egindako_jarduerak_direla_gorria_kolorea_duten_puntuek_adierazten_dute_oraindik_egitekoak_diren_jarduerak_daudela_jolastu_eta_jarduera_guztiak_bete)

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
