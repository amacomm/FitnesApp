package com.example.fitness

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.fitness.Activity.LoginActivity
import com.example.fitness.Page.BottomNavigationBar
import com.example.fitness.Page.NavHostContainer
import com.example.fitness.ui.theme.FitnessTheme
import com.example.fitness.utils.ApiService
import com.example.fitness.utils.LocationService
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startApp()
        val intent = Intent(this, ApiService::class.java)
        val dir = getCacheDir()
        val file = File( dir, "token.txt")
        intent.putExtra("path", file.absolutePath)
        intent.putExtra("page", "main")
        startService(intent)
    }
    private fun startApp(){
        setContent {
            val navController = rememberNavController()
            FitnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(navController = navController, this)
                        }, content = { padding ->
                            NavHostContainer(activity = this, navController = navController, padding = padding)
                        }
                    )
                    //HomePage()
                }
            }
        }
    }
}