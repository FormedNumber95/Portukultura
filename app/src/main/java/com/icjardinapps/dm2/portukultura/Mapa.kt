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
        //"Rialia" to Puzle::class.java,
        //"Zubia" to OtroActivity::class.java,
        "Jarrilla" to SopaDeLetras::class.java,
        //"Arrautza" to OtraActividad::class.java,
        //"Tren" to OtraActividad::class.java,
        //"LaGia" to OtraActividad::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el SupportMapFragment y esperar a que el mapa esté listo
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

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
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))!!
        markerColors[jarrillaMarker] = BitmapDescriptorFactory.HUE_RED

        arrautzaMarker = mMap.addMarker(MarkerOptions().position(XXmendekoArrautza).title("Arrautza"))!!
        markerColors[arrautzaMarker] = BitmapDescriptorFactory.HUE_RED

        trenGeltokiaMarker = mMap.addMarker(MarkerOptions().position(canillakoTrenGeltokia).title("Tren"))!!
        markerColors[trenGeltokiaMarker] = BitmapDescriptorFactory.HUE_RED

        laGiaMarker = mMap.addMarker(MarkerOptions().position(laGiakoJaia).title("LaGia"))!!
        markerColors[laGiaMarker] = BitmapDescriptorFactory.HUE_RED

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(laGiakoJaia, 15f))

        // Configurar el evento onClick para los marcadores
        mMap.setOnMarkerClickListener { marker ->
            if (markerColors[marker] == BitmapDescriptorFactory.HUE_YELLOW) {
                val activityClass = markerActivityMap[marker.title]

                if (activityClass != null) {
                    // Lanzar la actividad correspondiente
                    val intent = Intent(this, activityClass)
                    startActivity(intent)
                    return@setOnMarkerClickListener true  // Indicar que hemos manejado el evento
                }
            }
            false // Si no se cumple la condición del color amarillo o no hay actividad asociada, devolver false
        }
    }
}
