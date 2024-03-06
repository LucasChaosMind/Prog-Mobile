package io.explain.mytravel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.explain.mytravel.adapter.CustomAdapter
import io.explain.mytravel.adapter.OnItemClickListener
import io.explain.mytravel.models.LocationData
import io.explain.mytravel.repository.LocationRepository

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationRepository: LocationRepository;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuração do RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        locationRepository = LocationRepository(this)

        // Altere esta linha para usar os dados do banco de dados
        val locationDataList = locationRepository.fetchLocationDataFromDatabase()
        val adapter = CustomAdapter(locationDataList, this)
        recyclerView.adapter = adapter

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(OnMapReadyCallback { map ->
            googleMap = map

            for (locationData in locationDataList) {
                val latLng = LatLng(locationData.latitude, locationData.longitude)
                googleMap.addMarker(MarkerOptions().position(latLng).title(locationData.description))
            }
        })
    }

    override fun onItemClick(locationData: LocationData) {
        // Atualize o mapa com base no item clicado
        val latLng = LatLng(locationData.latitude, locationData.longitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f), 1000, null)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
   override fun onDeleteClick(description: String) {
        locationRepository.deleteLocation(description)
        updateLocationList()
    }

    fun onfabClickAdd(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, CadastraLocalizacao::class.java)
            startActivity(intent)
        }
    }

    private fun updateLocationList() {
        val locationDataList = locationRepository.fetchLocationDataFromDatabase()
        val adapter = CustomAdapter(locationDataList, this)
        recyclerView.adapter = adapter
    }
    private fun createDummyLocationData(): List<LocationData> {
        val dummyData = mutableListOf(
            LocationData(37.7749, -122.4194, "San Francisco"),
            LocationData(34.0522, -118.2437, "Los Angeles"),
            LocationData(40.7128, -74.0060, "New York City")
        )
        return dummyData
    }
}