package com.example.fitness.Page

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import com.example.fitness.TrackCard
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fitness.Activity.DataSaveActivity
import com.example.fitness.Activity.RecordActivity
import com.example.fitness.R
import com.example.fitness.utils.ApiService

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//@Preview
//fun HomePage(){
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(onClick = {}) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            }
//        },
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(text = "Home")
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.surface,
//                    titleContentColor = MaterialTheme.colorScheme.onSurface
//                ),
//                actions = {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(
//                            imageVector = Icons.Default.Notifications,
//                            contentDescription = "Favorite",
//                            tint = MaterialTheme.colorScheme.onSurfaceVariant
//                        )
//                    }
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Image(
//                            painter = rememberAsyncImagePainter(model = "https://afisha.yuga.ru/media/c4/4a/happy-1836445_1920__wbcwyny.jpg"),
//                            contentDescription = "Profile",
//                            modifier = Modifier
//                                .size(24.dp)
//                                .clip(CircleShape)
//                        )
//                    }
//                }
//            )
//        },
//        bottomBar ={
//            NavigationBar {
//                    NavigationBarItem(
//                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
//                        label = { Text(text = "Some") },
//                        selected = true,
//                        onClick = { }
//                    )
//                NavigationBarItem(
//                    icon = { Icon(Icons.Filled.Map, contentDescription = null) },
//                    label = { Text(text = "Some") },
//                    selected = false,
//                    onClick = { }
//                )
//            }
//        }
//    ) { values ->
//        values
//        val api = ApiService
//        val tracks = api.getHistory()
//        tracks.forEach{
//            TrackCard(
//                it,
//                User = "Some Gay",
//                createdAt = "16:48",
//                title = "First Outgoing",
//                description = "Light riding on bike",
//                modifier = Modifier.padding(8.dp)
//            )
//        }
//    }
//}

class Info{
    var name: String = "Some Gay"
    var date: String = "12/04/2023"
    var distance: Float =0f
    var speed: Float = 0f
    var time: String = "16:18"
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NavHostContainer(
    activity: ComponentActivity,
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,

        // set the start destination as home
        startDestination = "home",

        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            // route : Home
            composable("home") {

                Box(modifier = Modifier.fillMaxSize()){
                    var list = mutableListOf(Info())
                    var tracks by remember { mutableStateOf(list) }
                    val broadcastReceiver = object: BroadcastReceiver(){
                        override fun onReceive(p0: Context, p1: Intent) {
                            var info = Info()
                            info.name = p1.extras!!.get("username") as String
                            info.date = p1.extras!!.get("date") as String
                            info.distance = p1.extras!!.get("distance") as Float
                            info.speed = p1.extras!!.get("speed") as Float
                            info.time = p1.extras!!.get("time") as String
                            tracks.add(info)
                        }
                    }
                    activity.registerReceiver(broadcastReceiver, IntentFilter("history"))
                    tracks.forEach{
                        TrackCard(
                            it,
                            User = "Some Gay",
                            createdAt = "16:48",
                            title = "First Outgoing",
                            description = "Light riding on bike",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                Column(modifier = Modifier.fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom) {
                    FloatingActionButton(onClick = {
                        val intent = Intent(activity, DataSaveActivity::class.java)
                        activity.startActivity(intent)
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                }
                }
            }

            // route : search
//            composable("map") {
//                val intent = Intent(activity, RecordActivity::class.java)
//                activity.startActivity(intent)
//            }

            // route : profile
            composable("profile") {
                UserPage(activity)
            }
//            composable("Subscribers") {
//                PeoplePage()
//            }
//            composable("Subscriptions") {
//                PeoplePage()
//            }
        })

}

@Composable
fun BottomNavigationBar(navController: NavHostController,
                        activity: ComponentActivity) {

    NavigationBar {

        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text(text = stringResource(id = R.string.Home)) },
            selected = currentRoute == "home",
            onClick = {navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Map, contentDescription = null) },
            label = { Text(text = stringResource(id = R.string.Record)) },
            selected = currentRoute == "map",
            onClick = {
                val intent = Intent(activity, RecordActivity::class.java)
                activity.startActivity(intent)
                //navController.navigate("map")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.People, contentDescription = null) },
            label = { Text(text = stringResource(id = R.string.Profile)) },
            selected = currentRoute == "profile",
            onClick = { navController.navigate("profile")}
        )
    }
    }
