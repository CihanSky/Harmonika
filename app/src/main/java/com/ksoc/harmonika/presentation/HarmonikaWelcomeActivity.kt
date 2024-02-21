package com.ksoc.harmonika.presentation

import MusicViewModel
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.ksoc.harmonika.R
import com.ksoc.harmonika.ui.theme.HarmonikaTheme

class HarmonikaWelcomeActivity : AppCompatActivity() {

    private lateinit var musicViewModel: MusicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(getColor(R.color.custom_purple))
        )
        musicViewModel = ViewModelProvider(this)[MusicViewModel::class.java]
        setContent {
            HarmonikaTheme {
                AppContent(musicViewModel = musicViewModel, context = LocalContext.current)
            }
        }
    }
}

@Composable
fun AppContent(musicViewModel: MusicViewModel, context: Context) {
    var searchText by remember { mutableStateOf("") }
    var selectedItemType by remember { mutableStateOf("Tracks") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        HomeTitleAndLogo()
        Spacer(modifier = Modifier.padding(top = 50.dp))
        MediaDropdown(selectedItemType = selectedItemType) { newType ->
            selectedItemType = newType
        }
        Spacer(modifier = Modifier.padding(top = 30.dp))
        SearchOutlinedTextField(
            searchText = searchText,
            onSearchTextChanged = { newSearchText -> searchText = newSearchText }
        )
        Spacer(modifier = Modifier.padding(top = 30.dp))
        SearchButton(
            musicViewModel = musicViewModel,
            searchText = searchText,
            selectedItemType = selectedItemType,
            context = context
        )
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
            text = "Search your favorite \nTrack, Artist or Album!",
            style = TextStyle(
                fontSize = 22.sp,
            ),
            color = Color.White
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_music_note_24),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MediaDropdown(
    selectedItemType: String,
    onSelectedItemTypeChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val choices = listOf("Tracks", "Artists", "Albums")

    ExposedDropdownMenuBox(
        modifier = Modifier.padding(horizontal = 35.dp), // Add horizontal padding
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedItemType,
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
                    onSelectedItemTypeChanged(choice)
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
                Text(
                    "Enter your search",
                    style = TextStyle(color = Color.LightGray)
                ) // Set the placeholder text color to white
            },
            singleLine = true, // Set the singleLine modifier to true
            modifier = Modifier
                .background(color = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity))
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done), // Set imeAction to Done
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() } // Call onSearchDone when "Done" action is triggered
            ),
            textStyle = TextStyle(color = Color.White) // Set text color to white
        )
    }
}

@Composable
fun SearchButton(
    musicViewModel: MusicViewModel,
    searchText: String,
    selectedItemType: String,
    context: Context
) {
    Button(
        onClick = {
            if (searchText.isNotBlank()) {
                when (selectedItemType) {
                    "Tracks" -> musicViewModel.searchTracks(searchText) { searchResults ->
                        val intent = Intent(context, SearchResultActivity::class.java)
                        intent.putExtra("searchResults", searchResults.toTypedArray())
                        context.startActivity(intent)
                    }
                    "Artists" -> musicViewModel.searchArtists(searchText) {  searchResults ->
                        val intent = Intent(context, SearchResultActivity::class.java)
                        intent.putExtra("searchResults", searchResults.toTypedArray())
                        context.startActivity(intent)
                    }
//                    "Albums" -> musicViewModel.searchAlbums(searchText) { searchResults ->
//                        // Perform search for albums
//                    }
                }
            } else {
                Toast.makeText(context, "Please enter a search text", Toast.LENGTH_SHORT).show()
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
}


@Preview
@Composable
fun Preview_AppContent() {
    HarmonikaTheme {
        val musicViewModel = remember { MusicViewModel() } // Create an instance of MusicViewModel
        AppContent(musicViewModel, LocalContext.current)
    }
}
