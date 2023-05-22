package com.example.fitness.Page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fitness.R

@Composable
fun StartPage(navController: NavHostController){
    Column(modifier = Modifier.fillMaxSize()
        .padding(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center) {
        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier.padding(4.dp, 16.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = stringResource(id = R.string.Login),
                color = MaterialTheme.colorScheme.onPrimary)
        }
        OutlinedButton(
            onClick = { navController.navigate("register") },
            modifier = Modifier.padding(4.dp, 16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(text = stringResource(id = R.string.Registration),
                color = MaterialTheme.colorScheme.onBackground)
        }
    }
}