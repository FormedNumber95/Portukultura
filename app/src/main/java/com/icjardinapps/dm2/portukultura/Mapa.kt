package com.icjardinapps.dm2.portukultura

import android.content.Intent
import android.os.Bundle
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
 * Actividad que gestiona el mapa y la interacción con los marcadores.
 * Cada marcador tiene una actividad asociada, que solo se ejecuta si el marcador es de color amarillo.
 */

class Mapa : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: MapaBinding
    private lateinit var areilzaMarker: Marker
    private lateinit var rialiaMarker: Marker
    private lateinit var zubiMarker: Marker
    private lateinit var jarrillaMarker: Marker
    private lateinit var arrautzaMarker: Marker
    private lateinit var trenGeltokiaMarker: Marker
    private lateinit var laGiaMarker: Marker

    // Mapa para almacenar los colores de los marcadores
    private val markerColors = HashMap<Marker, Float>()

    // Mapa para asociar cada marcador con su actividad correspondiente
    private val markerActivityMap = mapOf(
        //"Areilza" to OtroActivity::class.java,
        // "Rialia" to Puzle::class.java,
        // "Zubia" to OtroActivity::class.java,
        "Jarrilla" to SopaDeLetras::class.java,
        // "Arrautza" to OtraActividad::class.java,
        // "Tren" to OtraActividad::class.java,
        // "LaGia" to OtraActividad::class.java
        )

    /**
     * Metodo llamado cuando se crea la actividad.
     * Configura el mapa y establece los marcadores.
     *
     * @param savedInstanceState El estado guardado de la actividad (si existe).
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el SupportMapFragment y esperar a que el mapa esté listo
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Metodo que se llama cuando el mapa está listo para ser utilizado.
     * Crea los marcadores, asigna colores y los agrega al mapa.
     *
     * @param googleMap El objeto de GoogleMap que representa el mapa.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val areilzaParkea = LatLng(43.32499609828457, -3.020763949816549)
        val rialiaMuseoa = LatLng(43.31871964219722, -3.013891264960716)
        val portugaletekoZubiEsekia = LatLng(43.32327983515113, -3.0171740054884357)
        val jarrilla = LatLng(43.321473128061704, -3.0171637139280474)
        val XXmendekoArrautza = LatLng(43.32027889068112, -3.015688498966755)
        val canillakoTrenGeltokia = LatLng(43.31860888436704, -3.015193226072288)
        val laGiakoJaia = LatLng(43.32065891272269, -3.0178038091884094)

        // Crear marcadores con colores y almacenarlos
        areilzaMarker = mMap.addMarker(
            MarkerOptions().position(areilzaParkea).title("Areilza")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        )!!
        markerColors[areilzaMarker] = BitmapDescriptorFactory.HUE_YELLOW

        rialiaMarker = mMap.addMarker(MarkerOptions().position(rialiaMuseoa).title("Rialia"))!!
        markerColors[rialiaMarker] = BitmapDescriptorFactory.HUE_RED

        zubiMarker = mMap.addMarker(MarkerOptions().position(portugaletekoZubiEsekia).title("Zubia"))!!
        markerColors[zubiMarker] = BitmapDescriptorFactory.HUE_RED

        jarrillaMarker = mMap.addMarker(MarkerOptions().position(jarrilla).title("Jarrilla")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))!!
        markerColors[jarrillaMarker] = BitmapDescriptorFactory.HUE_YELLOW

        arrautzaMarker = mMap.addMarker(MarkerOptions().position(XXmendekoArrautza).title("Arrautza"))!!
        markerColors[arrautzaMarker] = BitmapDescriptorFactory.HUE_RED

        trenGeltokiaMarker = mMap.addMarker(MarkerOptions().position(canillakoTrenGeltokia).title("Tren"))!!
        markerColors[trenGeltokiaMarker] = BitmapDescriptorFactory.HUE_RED

        laGiaMarker = mMap.addMarker(MarkerOptions().position(laGiakoJaia).title("LaGia"))!!
        markerColors[laGiaMarker] = BitmapDescriptorFactory.HUE_RED

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(laGiakoJaia, 15f))

        // Configurar el evento onClick para los marcadores
        mMap.setOnMarkerClickListener { marker ->
            // Si el marcador no es amarillo, no hacer nada (ni mostrar su título ni hacer la acción)
            if (markerColors[marker] != BitmapDescriptorFactory.HUE_YELLOW) {
                return@setOnMarkerClickListener false
            }

            // Si el marcador es amarillo, obtener la actividad correspondiente
            val activityClass = markerActivityMap[marker.title]

            if (activityClass != null) {
                // Lanzar la actividad correspondiente
                val intent = Intent(this, activityClass)
                startActivity(intent)
                return@setOnMarkerClickListener true  // Indicar que hemos manejado el evento
            }

            false  // Si no hay actividad asociada, devolver false
        }

        // Evitar que se muestren los títulos de los marcadores no amarillos
        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            /**
             * Metodo para personalizar la ventana de información de los marcadores.
             *
             * @param marker El marcador cuya ventana de información se está solicitando.
             * @return La vista que debe mostrarse en la ventana de información.
             */
            override fun getInfoWindow(marker: Marker): android.view.View? {
                // No mostrar información de la ventana de los marcadores que no sean amarillos
                return if (markerColors[marker] == BitmapDescriptorFactory.HUE_YELLOW) {
                    null  // Mostrar el contenido por defecto si es amarillo
                } else {
                    android.view.View(this@Mapa)  // No mostrar nada si no es amarillo
                }
            }

            /**
             * Metodo para obtener los contenidos de la ventana de información de los marcadores.
             *
             * @param marker El marcador cuya información se está solicitando.
             * @return La vista que debe mostrarse en la ventana de información.
             */
            override fun getInfoContents(marker: Marker): android.view.View? {
                // Devolver null para evitar que se muestre el título de los marcadores no amarillos
                return if (markerColors[marker] == BitmapDescriptorFactory.HUE_YELLOW) {
                    null  // Mostrar el contenido por defecto si es amarillo
                } else {
                    android.view.View(this@Mapa)  // No mostrar nada si no es amarillo
                }
            }
        })
    }
}
