package com.example.fitness.Page

import android.content.Intent
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import com.example.fitness.TrackCard
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitness.DataSaveActivity
import com.example.fitness.MainActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HomePage(){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Home")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Favorite",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = rememberAsyncImagePainter(model = "https://afisha.yuga.ru/media/c4/4a/happy-1836445_1920__wbcwyny.jpg"),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            )
        },
        bottomBar ={
            NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text(text = "Some") },
                        selected = true,
                        onClick = { }
                    )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Map, contentDescription = null) },
                    label = { Text(text = "Some") },
                    selected = false,
                    onClick = { }
                )
            }
        }
    ) { values ->
        LazyColumn(contentPadding = values) {
            items(20) {
                TrackCard(
                    User = "Some Gay",
                    createdAt = "16:48",
                    title = "First Outgoing",
                    description = "Light riding on bike",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
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
                LazyColumn(contentPadding = padding) {
                    items(20) {
                        TrackCard(
                            User = "Some Gay",
                            createdAt = "16:48",
                            title = "First Outgoing",
                            description = "Light riding on bike",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
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
            composable("map") {
                LazyColumn(contentPadding = padding) {
                    items(20) {
                        TrackCard(
                            User = "Some Gay",
                            createdAt = "16:48",
                            title = "First Outgoing",
                            description = "Light riding on bike",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            // route : profile
            composable("profile") {
                UserPage()
            }
        })

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    NavigationBar {

        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text(text = "Home") },
            selected = currentRoute == "home",
            onClick = {navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Map, contentDescription = null) },
            label = { Text(text = "Map") },
            selected = currentRoute == "map",
            onClick = { navController.navigate("map")}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.People, contentDescription = null) },
            label = { Text(text = "Profile") },
            selected = currentRoute == "profile",
            onClick = { navController.navigate("profile")}
        )
    }
    }
