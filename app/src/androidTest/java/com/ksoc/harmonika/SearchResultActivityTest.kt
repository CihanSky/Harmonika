package com.ksoc.harmonika.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.ksoc.harmonika.data.model.*
import com.ksoc.harmonika.ui.theme.HarmonikaTheme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchResultActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Test: Verify that the activity loads and displays content correctly for a Track search result
    @Test
    fun testSearchResultActivityWithTrackResults() {
        val mockTrack = Track(
            name = "Yesterday",
            artists = listOf(Artist(name = "Beatles", genres = listOf(), images = listOf())),
            album = Album(name = "Help!", release_date = "1965-08-06")
        )

        val searchResults = arrayOf(mockTrack)
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, SearchResultActivity::class.java)
        intent.putExtra(SearchResultActivity.EXTRA_SEARCH, searchResults)
        intent.putExtra(SearchResultActivity.EXTRA_FLOW, SearchFlow.TRACK)

        composeTestRule.activity.setIntent(intent)
        composeTestRule.activity.onCreate(Bundle())

        composeTestRule.setContent {
            HarmonikaTheme {
                SearchResultContent(searchResults = searchResults, searchFlow = SearchFlow.TRACK)
            }
        }

        // Check if track name is displayed
        composeTestRule.onNodeWithText("Yesterday").assertIsDisplayed()
        composeTestRule.onNodeWithText("Artist: Beatles").assertIsDisplayed()
    }

    // Test: Verify that the activity loads and displays content correctly for an Artist search result
    @Test
    fun testSearchResultActivityWithArtistResults() {
        val mockArtist = Artist(
            name = "Beatles",
            genres = listOf("Classic Rock"),
            images = listOf()
        )

        val searchResults = arrayOf(mockArtist)
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, SearchResultActivity::class.java)
        intent.putExtra(SearchResultActivity.EXTRA_SEARCH, searchResults)
        intent.putExtra(SearchResultActivity.EXTRA_FLOW, SearchFlow.ARTIST)

        composeTestRule.activity.setIntent(intent)
        composeTestRule.activity.onCreate(Bundle())

        composeTestRule.setContent {
            HarmonikaTheme {
                SearchResultContent(searchResults = searchResults, searchFlow = SearchFlow.ARTIST)
            }
        }

        // Check if artist name and genre are displayed
        composeTestRule.onNodeWithText("Beatles").assertIsDisplayed()
        composeTestRule.onNodeWithText("Classic Rock").assertIsDisplayed()
    }

    // Test: Verify that the activity loads and displays content correctly for an Album search result
    @Test
    fun testSearchResultActivityWithAlbumResults() {
        val mockAlbum = Album(
            name = "Help!",
            artists = listOf(Artist(name = "Beatles", genres = listOf(), images = listOf())),
            release_date = "1965-08-06"
        )

        val searchResults = arrayOf(mockAlbum)
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, SearchResultActivity::class.java)
        intent.putExtra(SearchResultActivity.EXTRA_SEARCH, searchResults)
        intent.putExtra(SearchResultActivity.EXTRA_FLOW, SearchFlow.ALBUM)

        composeTestRule.activity.setIntent(intent)
        composeTestRule.activity.onCreate(Bundle())

        composeTestRule.setContent {
            HarmonikaTheme {
                SearchResultContent(searchResults = searchResults, searchFlow = SearchFlow.ALBUM)
            }
        }

        // Check if album name is displayed
        composeTestRule.onNodeWithText("Help!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Beatles").assertIsDisplayed()
    }

    // Test: Verify that empty search results show no items
    @Test
    fun testSearchResultActivityWithEmptyResults() {
        val searchResults = emptyArray<Parcelable>()
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, SearchResultActivity::class.java)
        intent.putExtra(SearchResultActivity.EXTRA_SEARCH, searchResults)
        intent.putExtra(SearchResultActivity.EXTRA_FLOW, SearchFlow.TRACK)

        composeTestRule.activity.setIntent(intent)
        composeTestRule.activity.onCreate(Bundle())

        composeTestRule.setContent {
            HarmonikaTheme {
                SearchResultContent(searchResults = searchResults, searchFlow = SearchFlow.TRACK)
            }
        }

        // Verify that no results are displayed
        composeTestRule.onNodeWithText("No results found").assertIsDisplayed()
    }
}