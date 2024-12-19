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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

        areilzaMarker=mMap.addMarker(
            MarkerOptions().position(areilzaParkea).title("Areilza")
                .icon(BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_YELLOW))
        )!!
        rialiaMarker=mMap.addMarker(
            MarkerOptions().position(rialiaMuseoa).title("Rialia")
        )!!
        zubiMarker=mMap.addMarker(
            MarkerOptions().position(portugaletekoZubiEsekia).title("Zubia")
        )!!
        jarrillaMarker=mMap.addMarker(
            MarkerOptions().position(jarrilla).title("Jarrilla")
        )!!
        arrautzaMarker=mMap.addMarker(
            MarkerOptions().position(XXmendekoArrautza).title("Arrautza")
        )!!
        trenGeltokiaMarker=mMap.addMarker(
            MarkerOptions().position(canillakoTrenGeltokia).title("Tren")
        )!!
        laGiaMarker=mMap.addMarker(
            MarkerOptions().position(laGiakoJaia).title("LaGia")
        )!!

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(laGiakoJaia, 15f))

        // Configurar el evento onClick para los marcadores
        mMap.setOnMarkerClickListener { marker ->
            if (marker == jarrillaMarker) {
                // Lanzar la actividad SopaDeLetras
                val intent = Intent(this, SopaDeLetras::class.java)
                startActivity(intent)
                true // Indicar que hemos manejado el evento
            } else {
                false // Permitir que el mapa maneje otros eventos de marcador
            }
        }
    }
}
