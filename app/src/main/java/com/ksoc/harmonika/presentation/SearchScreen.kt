package com.ksoc.harmonika.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchScreen() {
    val queryState = remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = queryState.value,
            onValueChange = { queryState.value = it },
            label = { Text("Enter song, album, or artist") }
        )
        Button(onClick = {  }) {
            Text("Search")
        }
        // Display search results here
    }
}

@Preview
@Composable
fun PreviewSearchScreen() {
    // Preview search screen
}
