package com.ksoc.harmonika.presentation

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ksoc.harmonika.R
import com.ksoc.harmonika.data.model.Artist
import com.ksoc.harmonika.data.model.TrackItem
import com.ksoc.harmonika.ui.theme.HarmonikaTheme


class SearchResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setBackgroundDrawable(ColorDrawable(getColor(R.color.custom_purple)))
            setDisplayHomeAsUpEnabled(true) // Show the back button
        }
        // Extract the Serializable ArrayList from the intent
        val searchResults =
            intent.getParcelableArrayExtra("searchResults")?.filterIsInstance<TrackItem>()
                ?: emptyList()


        setContent {
            HarmonikaTheme {
                SearchResultContent(searchResults = searchResults.toList())
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Handle back button click event
        return true
    }
}

@Composable
fun SearchResultContent(searchResults: List<TrackItem>?) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
            .padding(bottom = 25.dp)
    ) {
        searchResults?.forEach { track ->
            TrackListItem(track = track)
        }
    }
}


/**
 * A composable function that displays a list item for a track.
 * The list item includes the track's image, name, and artist.
 * Also includes a click action for the track.
 *
 * @param track The track to be displayed.
 * @param onTrackClick The action to be performed when the track item is clicked.
 */
@Composable
fun TrackListItem(track: TrackItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 25.dp, start = 20.dp, end = 20.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
    ) {
        //        TrackImage(trackImage = track.trackImage, modifier = Modifier.size(size = 64.dp))
        Image(
            painter = painterResource(id = R.drawable.baseline_music_note_24),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
        )
        Column(
            modifier = Modifier
                .padding(all = 10.dp)
                .weight(weight = 1f)
        ) {

            Text(text = track.artists[0].name, style = typography.body1, color = Color.Black)
            Text(text = track.name, style = typography.h6, color = Color.DarkGray)
        }
    }
}


@Preview
@Composable
fun Preview_SearchResultActivity() {
    HarmonikaTheme {
        TrackListItem(TrackItem(name = "Yesterday", listOf(Artist("Beatles"))))
    }
}

