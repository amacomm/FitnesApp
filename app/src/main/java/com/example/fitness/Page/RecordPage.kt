package com.example.fitness.Page

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Paint.Align
import android.icu.text.SimpleDateFormat
import android.location.Location
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.registerReceiver
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.fitness.R
import com.example.fitness.utils.LocationService
import java.util.Date

@Composable
//@Preview
fun RecordPage(
    activity: ComponentActivity
){
    var distance by remember { mutableStateOf(0f) }
    var avgSpeed by remember { mutableStateOf(0f) }
    var time by remember { mutableStateOf(0L) }
    var timeStr by remember { mutableStateOf("00:00:00") }
    lateinit var lastLocation: Location
    var check1 by remember { mutableStateOf(false) }
    var check =false

    val broadcastReceiver = object: BroadcastReceiver(){
            override fun onReceive(p0: Context, p1: Intent) {
                val location = p1.extras!!.get("loc") as Location
                if(check)
                    distance+=lastLocation.distanceTo(location)

                lastLocation = location
                if (location.hasSpeed()){
                    avgSpeed = location.speed
                    time = location.time - lastLocation.time
                    val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
                    timeStr = simpleDateFormat.format(Date(time))
                }
                check = true
            }

        }
    activity.registerReceiver(broadcastReceiver, IntentFilter("location_update"))
    Box(){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                modifier = Modifier.padding(8.dp)
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
            modifier = Modifier.padding(8.dp)
                .weight(1f)) {
                Text(text = stringResource(id = R.string.Speed),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center)
                Text(text = avgSpeed.toString(),
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
                modifier = Modifier.padding(8.dp)
                    .weight(1f)) {
                Text(text = stringResource(id = R.string.Distance),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center)
                Text(text = distance.toString(),
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
            Row(verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(8.dp)
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
                if(check1){
                    val intent= Intent(activity, LocationService::class.java)
                    activity.stopService(intent)
                    check1=false
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