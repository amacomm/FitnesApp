package com.example.fitness.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
@Preview
fun PeopleCard(image:String = "https://c1.wallpaperflare.com/preview/952/665/252/young-sunset-male-guy.jpg",
                Name: String = "Some Guy",
                From: String = "Some Place",
               Signed: Boolean = false){
    Card(modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = null,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape),
                alignment = Alignment.Center
            )
            Column(modifier = Modifier
                .weight(1f)
                .padding(8.dp, 0.dp),
            verticalArrangement = Arrangement.Center) {
                Text(text = Name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                )
                Text(text = From,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            when{
                !Signed ->
                    OutlinedButton(onClick = { /*TODO*/ },
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Subscribe")
                }
                else ->
                    OutlinedButton(onClick = { /*TODO*/ },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "Unsubscribe")
                    }
            }

        }
    }
}