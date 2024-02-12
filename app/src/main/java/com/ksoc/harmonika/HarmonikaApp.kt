package com.ksoc.harmonika

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ksoc.harmonika.ui.theme.HarmonikaTheme

class HarmonikaApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HarmonikaTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray) // Adjust background color as needed,
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 75.dp)
        ) {
            Text(
                text = "Harmonika",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White
                ),
            )
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(90.dp)
            )
        }
        Spacer(modifier = Modifier.padding(top = 50.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Text(
                text = "Search your favorite \nSong, Artist or Album!",
                textAlign = TextAlign.Center, // Align text to the center
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White
                )
            )
        }
        Spacer(modifier = Modifier.padding(top = 50.dp))
        MediaDropdown()
        Spacer(modifier = Modifier.padding(top = 30.dp))
        SearchComponent(searchText = "MJ", onSearchTextChanged = {}, onSearchButtonClick = {})
    }
}


@Composable
fun MediaDropdown() {
    var expanded by remember { mutableStateOf(false) }
    val choices = listOf("Albums", "Songs", "Artists")
    var selectedChoice by remember { mutableStateOf(choices[0]) }

    Box(
        modifier = Modifier
            .padding(horizontal = 35.dp) // Add horizontal padding
    ) {
        OutlinedTextField(
            value = selectedChoice,
            onValueChange = { selectedChoice = it },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Expand menu")
                }
            },
            modifier = Modifier.fillMaxWidth().background(color = Color.White)
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            choices.forEach { choice ->
                DropdownMenuItem(onClick = {
                    selectedChoice = choice
                    expanded = false
                }) {
                    Text(text = choice)
                }
            }
        }
    }
}

@Composable
fun SearchComponent(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchButtonClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 35.dp) // Fill the entire width of the screen
            .imePadding()
    ){
        OutlinedTextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            placeholder = { Text("Enter search term") },
            modifier = Modifier.background(color = Color.White).fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Button(
            onClick = onSearchButtonClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
                Spacer(modifier = Modifier.width(4.dp)) // Add some spacing between icon and text
                Text(text = "Search")
            }
        }
    }
}


@Preview
@Composable
fun Preview_AppContent() {
    AppContent()
}

/*
        // Background Image
//        Image(
//            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your background image
//            contentDescription = null, // Provide a meaningful content description
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop // Adjust content scale as needed
//        )
* */
