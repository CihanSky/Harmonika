package com.ksoc.harmonika.presentation

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.ksoc.harmonika.R
import com.ksoc.harmonika.data.model.Album
import com.ksoc.harmonika.data.model.Artist
import com.ksoc.harmonika.data.model.SearchFlow
import com.ksoc.harmonika.data.model.Track
import com.ksoc.harmonika.ui.theme.HarmonikaTheme
import com.ksoc.harmonika.ui.theme.md_theme_dark_onSurface
import com.ksoc.harmonika.ui.theme.md_theme_dark_onTertiary


class SearchResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setBackgroundDrawable(ColorDrawable(getColor(R.color.custom_purple)))
            setDisplayHomeAsUpEnabled(true)
        }

        val searchResults = intent.getParcelableArrayExtra(EXTRA_SEARCH)
        val searchFlow = intent.getSerializableExtra(EXTRA_FLOW) as? SearchFlow

        setContent {
            HarmonikaTheme {
                SearchResultContent(searchResults = searchResults, searchFlow = searchFlow)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_SEARCH = "extra_search"
        const val EXTRA_FLOW = "extra_flow"
        fun getIntent(context: Context, arr: Array<out Parcelable>, searchFlow: SearchFlow) {
            val intent = Intent(context, SearchResultActivity::class.java)
            intent.putExtra(EXTRA_SEARCH, arr)
            intent.putExtra(EXTRA_FLOW, searchFlow)
            context.startActivity(intent)
        }
    }
}

@Composable
fun SearchResultContent(searchResults: Array<out Parcelable>?, searchFlow: SearchFlow?) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
            .padding(bottom = 25.dp)
    ) {
        when(searchFlow) {
            SearchFlow.TRACK -> {
                searchResults?.filterIsInstance<Track>()?.forEach { track ->
                    TrackListItem(track = track)
                }
            }
            SearchFlow.ARTIST -> {
                searchResults?.filterIsInstance<Artist>()?.forEach { artist ->
                    ArtistListItem(artist = artist)
                }
            }
            SearchFlow.ALBUM -> {
                searchResults?.filterIsInstance<Album>()?.forEach { album ->
                    AlbumListItem(album = album)
                }
            }
            else -> {}
        }
    }
}

@Composable
fun TrackListItem(track: Track) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = md_theme_dark_onTertiary)
    ) {
        val painter = rememberImagePainter(
            data = track.album.images.firstOrNull()?.url,
            builder = {
                placeholder(R.drawable.baseline_music_note_24)
                error(R.drawable.baseline_music_note_24)
            }
        )

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(75.dp)
                .padding(all = 10.dp),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        Column(
            modifier = Modifier
                .padding(all = 10.dp)
                .weight(weight = 1f)
        ) {

            Text(text = track.name, style = typography.h6, color = md_theme_dark_onSurface)
            Text(text = track.artists[0].name, style = typography.caption, color = md_theme_dark_onSurface.copy(alpha = 0.6f))
        }
    }
}

@Composable
fun ArtistListItem(artist: Artist) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = md_theme_dark_onTertiary)
    ) {
        val painter = rememberImagePainter(
            data = artist.images.firstOrNull()?.url,
            builder = {
                placeholder(R.drawable.baseline_music_note_24)
                error(R.drawable.baseline_music_note_24)
            }
        )

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(75.dp)
                .padding(all = 10.dp),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        Column(
            modifier = Modifier
                .padding(all = 10.dp)
                .weight(weight = 1f)
        ) {
            val genreText = if (!artist.genres.isNullOrEmpty()) {
                if (artist.genres.size >= 2) {
                    "${artist.genres[0]}, ${artist.genres[1]}"
                } else {
                    artist.genres[0]
                }
            } else {
                "mix"
            }

            Text(text = artist.name, style = typography.h6, color = md_theme_dark_onSurface)
            Text(text = genreText, style = typography.caption, color = md_theme_dark_onSurface.copy(alpha = 0.6f))
        }
    }
}

@Composable
fun AlbumListItem(album: Album) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = md_theme_dark_onTertiary)
    ) {
        val painter = rememberImagePainter(
            data = album.images.firstOrNull()?.url,
            builder = {
                placeholder(R.drawable.baseline_music_note_24)
                error(R.drawable.baseline_music_note_24)
            }
        )

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(75.dp)
                .padding(all = 10.dp),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        Column(
            modifier = Modifier
                .padding(all = 10.dp)
                .weight(weight = 1f)
        ) {

            Text(text = album.name, style = typography.h6, color = md_theme_dark_onSurface)
            Text(text = album.artists[0].name, style = typography.caption, color = md_theme_dark_onSurface.copy(alpha = 0.6f))
        }
    }
}

@Preview
@Composable
fun Preview_SearchResultActivity() {
    HarmonikaTheme {
        Column {
            TrackListItem(
                Track(
                    name = "Yesterday",
                    artists = listOf(Artist(name = "Beatles", genres = listOf(), images = listOf())),
                    album = Album(name = "Help!")
                )
            )
            ArtistListItem(
                Artist(
                    name = "Beatles",
                    genres = listOf("Classic Rock")
                )
            )
            AlbumListItem(
                Album(
                    name = "Help!",
                    artists = listOf(Artist(name = "Beatles", genres = listOf(), images = listOf())),
                )
            )
        }
    }
}


