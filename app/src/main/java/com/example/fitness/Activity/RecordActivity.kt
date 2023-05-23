package com.example.fitness.Activity

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.example.fitness.Page.RecordPage
import com.example.fitness.R
import com.example.fitness.ui.theme.FitnessTheme
import com.example.fitness.utils.LocationService
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.compass.CompassOverlay
import java.util.Date

class RecordActivity: ComponentActivity() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 101
    private lateinit var map : MapView
    lateinit var lastLocation: Location
    private lateinit var roadOverlay: Polyline
    private var roadOverlayIndex = 0
    private var check = 0

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
        roadOverlay = Polyline()
        roadOverlay.color = Color.parseColor("#00ff00")

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

        map.overlays.add(roadOverlay)
        roadOverlayIndex = map.overlays.size-1
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

    @Composable
    fun OSMMap(){
        AndroidView(factory ={
            View.inflate(it, R.layout.record_activity, null)
        },
        modifier = Modifier.fillMaxSize(),
            update = {
                map = it.findViewById(R.id.mapOSM)
                if(check==0){
                    mapInit()
                    check=1
                }
            }
        )
    }

    @Composable
    fun RecordPage(
        activity: ComponentActivity
    ){
        var distance by remember { mutableStateOf(0f) }
        var avgSpeed by remember { mutableStateOf(0f) }
        var time by remember { mutableStateOf(0L) }
        var timeStr by remember { mutableStateOf("00:00:00") }
        var check1 by remember { mutableStateOf(false) }

        val broadcastReceiver = object: BroadcastReceiver(){
            override fun onReceive(p0: Context, p1: Intent) {
                val location = p1.extras!!.get("loc") as Location
                if(::lastLocation.isInitialized) {
                    distance += lastLocation.distanceTo(location)/1000f
                    time += location.time - lastLocation.time
                }

                lastLocation = location
                if (location.hasSpeed()){
                    avgSpeed = (location.speed/3.6f)
                    val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
                    val date = Date(time)
                    date.hours-=4
                    timeStr = simpleDateFormat.format(date)

                    val point =GeoPoint(location.latitude, location.longitude)
                    (map.overlays[roadOverlayIndex] as Polyline).addPoint(point)
                    (map.overlays[roadOverlayIndex-1] as Marker).position =point
                    map.controller.setCenter(point)
                    map.invalidate()
                }
            }

        }
        activity.registerReceiver(broadcastReceiver, IntentFilter("location_update"))
        Box(){
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Column(modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)) {
                    OSMMap()
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = stringResource(id = R.string.Time),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center)
                        Text(text = timeStr,
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center)
                    }
                    Divider()
                    Row(verticalAlignment = Alignment.Bottom,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)) {
                        Text(text = stringResource(id = R.string.Speed),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center)
                        Text(text = "%.2f".format(avgSpeed),
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center)
                        Text(text = "km/h",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center)
                    }
                    Divider()
                    Row(verticalAlignment = Alignment.Bottom,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)) {
                        Text(text = stringResource(id = R.string.Distance),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center)
                        Text(text = "%.2f".format(distance),
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center)
                        Text(text = "km",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center)
                    }
                }

                Row(verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)){}
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row(){
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        FloatingActionButton(onClick = { /*TODO*/ },
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer) {
                            Icon(painter = painterResource(id = R.drawable.baseline_pedal_bike_24) , contentDescription = "Cycling")
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        LargeFloatingActionButton(onClick = {
                            if(::lastLocation.isInitialized){
                                val intent= Intent(activity, LocationService::class.java)
                                activity.stopService(intent)
                                check1=false
                                activity.finish()
                            }
                            else{
                                val intent = Intent(activity, LocationService::class.java)
                                activity.startForegroundService(intent)
                                check1=true
                            }
                        },
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(text="Start",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f)) {}
                }
            }
        }
    }
}