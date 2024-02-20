package com.ksoc.harmonika.presentation

import MusicViewModel
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ksoc.harmonika.R
import com.ksoc.harmonika.data.model.Artist
import com.ksoc.harmonika.data.model.TrackItem
import com.ksoc.harmonika.ui.theme.HarmonikaTheme

class SearchActivity : AppCompatActivity() {

    private val viewModel: MusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            SearchScreen()
            HarmonikaTheme {
                TrackListItem(TrackItem(name = "Yesterday", artists = listOf(Artist(name = "Beatles"))))
//                AllTracksScreen()
            }
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
            Text(text = track.name, style = typography.h5, color = Color.Red)
        }
    }
}

//todo: remove
@Composable
fun AllTracksScreen(
    //    navController: NavController,
    //    trackViewModel: TrackViewModel = hiltViewModel()
) {
    //    val trackState = trackViewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.custom_purple))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
        ) {
            Spacer(modifier = Modifier.height(31.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_music_note_24),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable { }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Popular Pop Tracks",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, top = 6.dp)
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(colorResource(id = androidx.appcompat.R.color.material_blue_grey_800))
                        .align(Alignment.CenterVertically)

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_music_note_24),
                        contentDescription = "play",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(6.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.LightGray),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(12.dp)
                )
                Spacer(modifier = Modifier.weight(2.2f))
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.LightGray),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(12.dp)
                    )
                    Image(
                        painter = painterResource(id = androidx.appcompat.R.drawable.abc_ic_arrow_drop_right_black_24dp),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.LightGray),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(12.dp)
                    )
                }
            }
            //            LazyColumn(
            //                modifier = Modifier
            //                    .fillMaxWidth()
            //                    .padding(start = 4.dp, top = 6.dp)
            //            ) {
            //                items(10) { tracks ->
            //                    TrackListItem(
            //                        track = TrackItem(name="yesterday", listOf(Artist("Beatles"))),
            //                    )
            //                }
            //            }


            //        if (trackState.error.isNotBlank()) {
            //            Text(
            //                text = trackState.error,
            //                color = MaterialTheme.colors.error,
            //                textAlign = TextAlign.Center,
            //                modifier = Modifier
            //                    .fillMaxWidth()
            //                    .padding(horizontal = 20.dp)
            //                    .align(Alignment.Center)
            //            )
            //        }
            //        if (trackState.isLoading) {
            //            CircularProgressIndicator(
            //                modifier = Modifier.align(Alignment.Center),
            //                color = Color.White
            //
            //            )
            //        }
        }
    }
}
@Preview
@Composable
fun Preview_SearchResultActivity() {
    HarmonikaTheme {
        TrackListItem(TrackItem(name = "Yesterday", listOf(Artist("Beatles"))))
//        AllTracksScreen()
    }
}

