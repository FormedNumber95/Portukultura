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

class Mapa : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: MapaBinding

    private val markerColors = HashMap<Marker, Float>()

    // Lista ordenada de marcadores
    private var markersList: MutableList<Marker> = mutableListOf()

    // Índice del marcador activo
    private var activeMarkerIndex = 0

    // Mapa de actividades asociadas a cada marcador
    private lateinit var markerActivities: Map<Marker, Class<out AppCompatActivity>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

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
                0 -> SopaDeLetras::class.java
                1 -> Presentacion::class.java
                2 -> SopaDeLetras::class.java
                3 -> SopaDeLetras::class.java
                4 -> SopaDeLetras::class.java
                5 -> SopaDeLetras::class.java
                6 -> SopaDeLetras::class.java
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
                    val intent = Intent(this, activityClass)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("activeMarkerIndex", activeMarkerIndex)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        activeMarkerIndex = savedInstanceState.getInt("activeMarkerIndex", 0)
    }
}
