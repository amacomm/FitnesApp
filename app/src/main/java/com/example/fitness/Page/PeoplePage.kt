package com.example.fitness.Page

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.fitness.R
import com.example.fitness.client.apis.UsersApi
import com.example.fitness.utils.ApiService
import com.example.fitness.utils.PeopleCard
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeoplePage(activity: ComponentActivity){
    val list = mutableListOf(Info())
    val users by remember { mutableStateOf(list) }
    val broadcastReceiver = object: BroadcastReceiver(){
        override fun onReceive(p0: Context, p1: Intent) {
            var info = Info()
            info.name = p1.extras!!.get("username") as String
            info.date = p1.extras!!.get("date") as String
            users.add(info)
        }
    }
    activity.registerReceiver(broadcastReceiver, IntentFilter("people"))
    val intent = Intent(activity, ApiService::class.java)
    intent.putExtra("page", "people")
    activity.startService(intent)
    Column() {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.Subscriptions))
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface
            )
        )
        Column(modifier = Modifier.padding(8.dp)) {
            users.forEach {
                PeopleCard(it)
            }
        }
    }
}