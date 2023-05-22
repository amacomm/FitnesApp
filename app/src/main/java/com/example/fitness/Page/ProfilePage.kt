package com.example.fitness.Page

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.text.SimpleDateFormat
import android.location.Location
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.fitness.R
import com.example.fitness.client.apis.UsersApi
import com.example.fitness.client.models.UserDto
import com.example.fitness.utils.ApiService
import java.io.File
import java.time.LocalDateTime
import java.util.Date

@Composable
fun ProfilePage(context: ComponentActivity) {
    var name by remember { mutableStateOf("Name") }
    var gender by remember { mutableStateOf(0) }
    var dateOfBirth by remember { mutableStateOf(LocalDateTime.now().toString()) }
    val broadcastReceiver = object: BroadcastReceiver(){
        override fun onReceive(p0: Context, p1: Intent) {
            name = p1.extras!!.get("username") as String
            gender = p1.extras!!.get("usergender") as Int
            dateOfBirth = p1.extras!!.get("userdate") as String
        }
    }
    context.registerReceiver(broadcastReceiver, IntentFilter("userdata"))
    val intent = Intent(context, ApiService::class.java)
    intent.putExtra("page", "profile")
    context.startService(intent)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        Card(modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )){
            Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(model = "https://c1.wallpaperflare.com/preview/952/665/252/young-sunset-male-guy.jpg"),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    alignment = Alignment.Center
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = when (gender) {
                    0 -> stringResource(id = R.string.Male)
                    else -> stringResource(id = R.string.Female)
                },
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = dateOfBirth,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.padding(4.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    TextButton(onClick = {}){
                        Column() {
                            Text(text = stringResource(id = R.string.Subscriptions),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = "108",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    TextButton(onClick = {}){
                    Column() {
                        Text(text = stringResource(id = R.string.Subscribers),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "29",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface)
                    }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            modifier = Modifier.padding(4.dp)
                    ) {
                        Text(text = stringResource(id = R.string.Edit))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Card(modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )){

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                Text(text = stringResource(id = R.string.Week),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.padding(4.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically){
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = stringResource(id = R.string.Distance),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center)
                        Text(text = "125 km",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center)
                    }
                    Divider(modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(0.dp, 4.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = stringResource(id = R.string.Time),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center)
                        Text(text = "12h 25min",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center)
                    }
                    Divider(modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(0.dp, 4.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = stringResource(id = R.string.Height),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center)
                        Text(text = "1084 m",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center)
                    }
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {

                    Column() {
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically) {
                            Divider(Modifier.weight(1f))
                            Text(text = "88 km",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(4.dp, 0.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.typography.titleLarge.fontSize.value.dp * 1.5f))
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically) {
                            Divider(Modifier.weight(1f))
                            Text(text = "44 km",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(4.dp, 0.dp)
                            )
                        }
                    }
                        Column(modifier = Modifier.padding(top = (MaterialTheme.typography.bodyLarge.fontSize.value/2f).dp,
                            end = 56.dp)) {
                            Row(modifier = Modifier
                                .fillMaxWidth(),
                                verticalAlignment = Alignment.Bottom){
                                (1..11).forEach(){
                                    Box(modifier = Modifier
                                        .background(MaterialTheme.colorScheme.primary)
                                        .size(
                                            4.dp,
                                            MaterialTheme.typography.titleLarge.fontSize.value.dp * 5f / 12f * it
                                        )
                                        .clip(MaterialTheme.shapes.large)
                                        .aspectRatio(3f / 2f)
                                        .padding(8.dp))
                                    Spacer(modifier = Modifier
                                        .weight(1f))
                                }
                                Box(modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primary)
                                    .size(
                                        4.dp,
                                        MaterialTheme.typography.titleLarge.fontSize.value.dp * 5f
                                    )
                                    .clip(MaterialTheme.shapes.large)
                                    .aspectRatio(3f / 2f)
                                    .padding(8.dp))
                            }
                            Divider()
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center) {
                                Text(text = stringResource(id = R.string.April),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(text = stringResource(id = R.string.May),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f))
                                Text(text = stringResource(id = R.string.June),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f))
                            }
                        }

                }
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Card(modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )){
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.Media),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center)
            }
                Row(modifier = Modifier.padding(8.dp)) {
                    (1..4).forEach(){
                        Image(
                            painter = rememberAsyncImagePainter(model = "https://classpic.ru/wp-content/uploads/2018/11/33095/les-i-gory-otrazhajutsja-v-ozere-v-sirenevom-cvete-640x360.jpg"),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(MaterialTheme.shapes.large)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
        }
    }
}