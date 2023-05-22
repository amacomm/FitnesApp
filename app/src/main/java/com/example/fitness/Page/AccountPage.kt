package com.example.fitness.Page

import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fitness.R
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountPage(navController: NavHostController){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        var NameValue by remember { mutableStateOf("") }
        var SurnameValue by remember { mutableStateOf("") }
        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp),
            value = NameValue,
            onValueChange = { NameValue = it },
            label = { Text(stringResource(id = R.string.Name)) },
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp),
            value = SurnameValue,
            onValueChange = { SurnameValue = it },
            label = { Text(stringResource(id = R.string.Surname)) },
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.time = Date()
        var date by remember { mutableStateOf("") }
//        val datePickerDialog = DatePickerDialog(
//            context,
//            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
//                date = "$dayOfMonth/${month+1}/$year"
//            }, year, month, day
//        )
        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp),
            value = date,
            onValueChange = { date = it },
            label = { Text(stringResource(id = R.string.Birthday)) },
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
        Row(modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically){
            Text(text = stringResource(id = R.string.Male),
            modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            var checkedState by remember { mutableStateOf(true) }
            Switch(
                checked = checkedState,
                onCheckedChange = { checkedState = it },
                modifier = Modifier.weight(1f),
            )
            Text(text = stringResource(id = R.string.Female),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center)
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(4.dp, 16.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = stringResource(id = R.string.Save),
                color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}