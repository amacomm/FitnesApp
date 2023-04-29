package com.example.fitness.Page

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberAsyncImagePainter
import com.example.fitness.MainActivity
import com.example.fitness.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview
fun SaveDataPage(activity: ComponentActivity){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = "Save training") },
            navigationIcon = {
                IconButton(onClick = {
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    activity.startActivity(intent)
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back button")
                }
            },
            actions = {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text="Save",
                    color = MaterialTheme.colorScheme.primary)
                }
            }
        )
        Card(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            Column(modifier = Modifier
                .fillMaxSize()) {
                Image(
                    painter = rememberAsyncImagePainter(model = "https://i.pinimg.com/originals/8b/c7/e5/8bc7e577a55598b8687de2ccc2fd8a0e.png"),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.large)
                        .aspectRatio(3f / 2f)
                )
                var TraningName = remember { mutableStateOf("") }
                var TrainingDescription = remember { mutableStateOf("") }
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp),
                    value = TraningName.value,
                    onValueChange = { if (it.length <= 20) TraningName.value = it },
                    label = { Text("Training name") },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    )
                )
                OutlinedTextField(
                    maxLines = 4,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 120.dp)
                        .padding(8.dp, 0.dp),
                    value = TrainingDescription.value,
                    onValueChange = { TrainingDescription.value = it },
                    label = { Text("Training description") },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    )
                )

                OutlinedCard(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    Row() {
                        FloatingActionButton(onClick = { /*TODO*/ },
                            modifier = Modifier.padding(4.dp),
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.extraSmall){
                            Icon(imageVector = Icons.Default.PhotoCamera,
                                modifier = Modifier.padding(0.dp),
                                contentDescription = "AddPhoto"
                            )
                        }
                    }
                }

                var expanded by remember { mutableStateOf(false) }
                val listItems = arrayOf(
                    Pair("Cycling", R.drawable.baseline_pedal_bike_24),
                    Pair("Running", R.drawable.baseline_directions_run_24),
                    Pair("Walking", R.drawable.baseline_directions_walk_24))
                var selectedItem by remember {mutableStateOf(listItems[0])}
                Column() {
                    OutlinedTextField(
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(selectedItem.second),
                                contentDescription = "Cycling"
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    expanded = !expanded
                                }
                            ) {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp),
                        label = { Text(text = "Type of training") },
                        value = selectedItem.first,
                        onValueChange = {},
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        listItems.forEach {
                            DropdownMenuItem(
                                text = { Text(it.first) },
                                onClick = {
                                    selectedItem = it
                                    expanded = false
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(it.second),
                                        contentDescription = "Cycling"
                                    )
                                })
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center){
                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(text = "Delete training",
                            color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }


}