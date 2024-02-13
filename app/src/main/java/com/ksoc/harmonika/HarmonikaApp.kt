package com.ksoc.harmonika

import TrackItem
import android.graphics.drawable.ColorDrawable
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ksoc.harmonika.ui.theme.HarmonikaTheme
import kotlinx.coroutines.launch
import searchSongs

class HarmonikaApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(
            getColor(R.color.custom_purple))
        ) // Change the color as needed

        setContent {
            HarmonikaTheme {
                 Surface {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    val searchTextState = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF9561a1),
                        Color(0xFF122259)
                    )
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            HomeTitleAndLogo()
            Spacer(modifier = Modifier.padding(top = 50.dp))
            MediaDropdown()
            Spacer(modifier = Modifier.padding(top = 30.dp))
            SearchOutlinedTextField(
                searchText = searchTextState.value,
                onSearchTextChanged = { newText -> searchTextState.value = newText })
            Spacer(modifier = Modifier.padding(top = 30.dp))
            SearchButton()
        }
    }
}


@Composable
fun HomeTitleAndLogo() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 75.dp)
    ) {
        Text(
            text = "Search your favorite \nSong, Artist or Album!",
            style = TextStyle(
                fontSize = 22.sp,
            ),
            color = Color.White
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_music_note_24),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
//            colorFilter = ColorFilter.tint(Color(0xFF122259))
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MediaDropdown() {
    var expanded by remember { mutableStateOf(false) }
    val choices = listOf("Songs", "Artists", "Albums")
    var selectedChoice by remember { mutableStateOf(choices[0]) }

    ExposedDropdownMenuBox(
        modifier = Modifier.padding(horizontal = 35.dp), // Add horizontal padding
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedChoice,
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            textStyle = TextStyle(color = Color.White),
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
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
fun SearchOutlinedTextField(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp) // Fill the entire width of the screen
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { newSearchText ->
                onSearchTextChanged(newSearchText) // Call the provided function with the new search text
            },
            placeholder = {
                Text("Enter your search",
                style = TextStyle(color = Color.White)) // Set the placeholder text color to white
            },
            singleLine = true, // Set the singleLine modifier to true
            modifier = Modifier
                .background(color = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity))
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done), // Set imeAction to Done
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() } // Call onSearchDone when "Done" action is triggered
            ),
        )
    }
}

@Composable
fun SearchButton() {
    val scope = rememberCoroutineScope()
    var searchResults by remember { mutableStateOf<List<TrackItem>?>(null) }
    val searchText = "Yesterday"

    Button(
        onClick = {
            scope.launch {
                searchSongs(searchText)?.let { results ->
                    searchResults = results
                }
            }
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Search, contentDescription = "Search")
            Spacer(modifier = Modifier.width(4.dp)) // Add some spacing between icon and text
            Text(text = "Search")
        }
    }

    searchResults?.let { results ->
        if (results.isNotEmpty()) {
            Column {
                Text("Search Results:")
                Spacer(modifier = Modifier.height(8.dp))
                results.forEach { track ->
                    Text("${track.name} by ${track.artists.joinToString(", ") { it.name }}")
                }
            }
        } else {
            Text("No results found")
        }
    }
}


@Preview
@Composable
fun Preview_AppContent() {
    AppContent()
}