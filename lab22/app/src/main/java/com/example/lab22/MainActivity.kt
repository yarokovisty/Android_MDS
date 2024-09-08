package com.example.lab22


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.lab22.databinding.ActivityMainBinding
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var locationManager: LocationManager? = null
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1

    var long = 0.0
    var lat = 0.0
    var currnetlong:Double = 0.0
    var currentlat:Double = 0.0

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {

            currentlat = location.latitude
            currnetlong = location.longitude
            showInfo(location)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        starTracking()
    }


    private fun starTracking(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 1000, 10f, locationListener)
        locationManager?.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER, 1000, 10f, locationListener)
    }


    private fun showInfo(location: Location) {
        val isGpsOn = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkOn = locationManager!!.
        isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (location.provider == LocationManager.GPS_PROVIDER) {
            binding.gpsCoords.text = getString(R.string.gps_coords, location.latitude.toString(), location.longitude.toString())
        }
        if (location.provider == LocationManager.NETWORK_PROVIDER) {
            binding.networkCoords.text = getString(R.string.network_coords, location.latitude.toString(), location.longitude.toString())

        }
        check(location)
    }

    private fun check(location: Location){

        val dist  = distance(location.longitude, location.latitude, long, lat)
        findViewById<TextView>(R.id.dist).text = dist.toString()
        findViewById<TextView>(R.id.dist).setTextColor(Color.RED)
        if (dist <= 100){
            findViewById<TextView>(R.id.dist).setTextColor(Color.GREEN)
        }

    }


    fun addCoords(view: View){
        long = Random.nextDouble(currnetlong-0.02, currnetlong+0.02)
        lat = Random.nextDouble(currentlat-0.02, currentlat+0.02)

        binding.networkInmem.text = getString(R.string.gps_coords, lat.toString(), long.toString())
        
        binding.dist.text = getString(R.string.unknown)
    }

    private fun distance(lon1: Double, lat1: Double, lon2: Double, lat2: Double):Double{
        var R = 6378.137
        var dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180
        var dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180
        var a = sin(dLat/2) * sin(dLat/2) +
                cos(lat1 * Math.PI / 180) * cos(lat2 * Math.PI / 180) *
                sin(dLon/2) * sin(dLon/2)
        var c = 2 * atan2(sqrt(a), sqrt(1-a))
        var d = R * c
        return d * 1000
    }




}