package com.example.fitness.Page

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fitness.MainActivity
import com.example.fitness.R
import com.example.fitness.client.apis.UsersApi
import com.example.fitness.client.infrastructure.ServerException
import com.example.fitness.client.models.AuthRequest
import com.example.fitness.utils.ApiService
import java.io.File
import java.io.FileWriter
import kotlin.concurrent.thread


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(context: ComponentActivity,
              navController: NavHostController){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
        var LoginValue by remember { mutableStateOf("") }
        var PasswordValue by remember { mutableStateOf("") }
        val LoginField = OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp),
            value = LoginValue,
            onValueChange = { LoginValue = it },
            label = { Text(stringResource(id = R.string.Login)) },
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
        val PasswordField = OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp),
            value = PasswordValue,
            onValueChange = { PasswordValue = it },
            label = { Text(stringResource(id = R.string.Password)) },
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
        LoginField
        PasswordField
        Button(
            onClick = {
                thread {
                    try {
                        val dir = context.getCacheDir()
                        val file = File( dir, "token.txt")
                        file.createNewFile()
                        val userApi = UsersApi("https://dkz1z6k5-7125.euw.devtunnels.ms/")
                        var auth = userApi.apiUsersLoginPost(AuthRequest(LoginValue, PasswordValue))
                        val writer = FileWriter(file.absoluteFile)
                        writer.append(auth.accessToken)
                        writer.flush()
                        writer.close()
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        context.finish()
                    }
                    catch (e: ServerException){

                    }
                }
                      },
            modifier = Modifier.padding(4.dp, 16.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = stringResource(id = R.string.Login),
                color = MaterialTheme.colorScheme.onPrimary)
        }
    }

}