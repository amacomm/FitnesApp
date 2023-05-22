package com.example.fitness.Activity

import android.Manifest
import android.content.BroadcastReceiver
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.fitness.Page.RecordPage
import com.example.fitness.ui.theme.FitnessTheme
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.compass.CompassOverlay

class RecordActivity: ComponentActivity() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 101
    private lateinit var map : MapView
    //private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContent {
            FitnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecordPage(this@RecordActivity)
                }
            }
        }
        //getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
//        setContentView(R.layout.record_activity)
//        map = findViewById(R.id.mapOSM)
//        mapInit()
    }
    override fun onResume() {
        super.onResume()
//        if(!::broadcastReceiver.isInitialized)
//            broadcastReceiver = object: BroadcastReceiver(){
//                override fun onReceive(p0: Context, p1: Intent) {
//                    val location = p1.extras!!["loc"] as Location
//                    if(::lastLocation.isInitialized){
//                        distance+=lastLocation.distanceTo(location)
//                        tvDistance.setText("%.2f км".format(distance/1000))
//                    }
//
//                    lastLocation = location
//                    if (location.hasSpeed()){
//                        arrTime.add(location.time)
//                        tvAvgSpeed.setText("%.2f км/ч".format(arrSpeed.average()))
//                        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
//                        val date: String = simpleDateFormat.format(Date(arrTime.last()-arrTime.first()))
//                        tvTime.setText(date)
//                        arrSpeed.add(location.speed)
//                        tvSpeed.setText("%.2f км/ч".format(location.speed))
//                    }
//                }
//
//            }
//        registerReceiver(broadcastReceiver, IntentFilter("location_update"))
    }
    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
//        if(!::broadcastReceiver.isInitialized)
//            unregisterReceiver(broadcastReceiver)
    }

    private fun mapInit(){
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(18.0)

        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
        map.setMultiTouchControls(true)

        val compassOverlay = CompassOverlay(this, map)
        compassOverlay.enableCompass()
        map.overlays.add(compassOverlay)

        val point = GeoPoint(53.301083, 50.287331)

        val marker = Marker(map)
        marker.position=point
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        map.overlays.add(marker)

        map.controller.setCenter(point)

        //map.overlays.add(roadOverlay)
        //roadOverlayIndex = map.overlays.size-1
    }

    private fun requestPermission()
    {
        when {
            checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {

            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Toast.makeText(this, "Storage permission is requires, pleas allow from settings.", Toast.LENGTH_LONG).show()
            }
            else -> {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSIONS_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                } else {
                }
                return
            }
            else -> {
            }
        }
    }
}