package com.example.fitness.Activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitness.Page.AccountPage
import com.example.fitness.Page.LoginPage
import com.example.fitness.Page.RegistrationPage
import com.example.fitness.Page.StartPage
import com.example.fitness.ui.theme.FitnessTheme

class LoginActivity: ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "start") {
                        composable(route = "start") {
                            StartPage(navController)
                        }
                        composable(route = "login"){
                            LoginPage(this@LoginActivity, navController)
                        }
                        composable(route = "register"){
                            RegistrationPage(navController)
                        }
                        composable(route = "account"){
                            AccountPage(navController)
                        }
                    }
                }
            }
        }
    }
}