package com.ksoc.harmonika

import android.content.Context
import android.os.Parcelable
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ksoc.harmonika.data.model.Artist
import com.ksoc.harmonika.data.model.SearchFlow
import com.ksoc.harmonika.presentation.HomeTitleAndLogo
import com.ksoc.harmonika.presentation.MediaDropdown
import com.ksoc.harmonika.presentation.SearchButton
import com.ksoc.harmonika.presentation.SearchResultActivity
import com.ksoc.harmonika.ui.theme.HarmonikaTheme
import com.ksoc.harmonika.viewmodel.MusicViewModel
import kotlinx.parcelize.Parcelize
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


@Parcelize
data class Track(val name: String, val id: Int) : Parcelable

// Test for HomeScreen UI elements (Title and Logo)
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyHomeScreenElements() {
        composeTestRule.setContent {
            HarmonikaTheme {
                HomeTitleAndLogo()
            }
        }

        // Check if the title and logo are displayed
        composeTestRule.onNodeWithText("Search your favorite \nTrack, Artist or Album!")
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Search").assertIsDisplayed()
    }
}

// Test for Media Dropdown UI behavior
@RunWith(AndroidJUnit4::class)
class MediaDropdownTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyMediaDropdown() {
        composeTestRule.setContent {
            var selectedType by remember { mutableStateOf("Tracks") }
            HarmonikaTheme {
                MediaDropdown(selectedItemType = selectedType) { newType ->
                    selectedType = newType
                }
            }
        }

        // Check if the dropdown is displayed
        composeTestRule.onNodeWithText("Tracks").assertIsDisplayed()

        // Open the dropdown and select an option
        composeTestRule.onNodeWithText("Tracks").performClick()
        composeTestRule.onNodeWithText("Artists").performClick()

        // Verify that the selected item changes
        assertEquals("Artists", selectedType)
    }
}

// Test for SearchButton with empty input
@RunWith(AndroidJUnit4::class)
class SearchButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Mocking dependencies
    val mockMusicViewModel: MusicViewModel = mock()
    val mockContext: Context = mock()
    val mockFocusManager: FocusManager = mock()

    @Test
    fun verifySearchButtonWithEmptyInput() {
        composeTestRule.setContent {
            HarmonikaTheme {
                SearchButton(
                    musicViewModel = mockMusicViewModel,
                    searchText = "",
                    selectedItemType = "Tracks",
                    context = mockContext,
                    focusManager = mockFocusManager
                )
            }
        }

        // Click the search button
        composeTestRule.onNodeWithText("Search").performClick()

        // Verify that a Toast message appears (error handling for empty search)
        composeTestRule.onNodeWithText("Please enter a search text").assertExists()
    }
}

// Test for SearchButton with valid input
@RunWith(AndroidJUnit4::class)
class SearchButtonValidInputTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Mocking dependencies
    val mockMusicViewModel: MusicViewModel = mock()
    val mockContext: Context = mock()
    val mockFocusManager: FocusManager = mock()

    @Test
    fun verifySearchButtonWithValidInput() {
        var searchText = "Beatles"
        var selectedItemType = "Artists"

        composeTestRule.setContent {
            HarmonikaTheme {
                SearchButton(
                    musicViewModel = mockMusicViewModel,
                    searchText = searchText,
                    selectedItemType = selectedItemType,
                    context = mockContext,
                    focusManager = mockFocusManager
                )
            }
        }

        // Click the search button
        composeTestRule.onNodeWithText("Search").performClick()

        // Verify that the correct ViewModel method is called with the correct parameters
        verify(mockMusicViewModel).searchTracks("Beatles", "Artists")
    }
}

// Test for passing search results to SearchResultActivity
@RunWith(AndroidJUnit4::class)
class SearchResultActivityTest {

    @Test
    fun testSearchResultActivityIntent() {
        // Create real instances of Track and Artist
        val mockTrack = Track(name = "Imagine", id = 1)
        val mockArtist = Artist(name = "John Lennon", id = 101)

        // Create an intent with the mock objects
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val searchResults = arrayOf(mockTrack, mockArtist)
        val intent = SearchResultActivity.getIntent(context, searchResults, SearchFlow.TRACK)

        // Ensure the intent is properly created with the right extras
        val receivedSearchResults =
            intent.extras?.getParcelableArray(SearchResultActivity.EXTRA_SEARCH) // Get the parcelable array

        // Make sure the results are not null and cast to expected type
        assertNotNull(receivedSearchResults)

        // Cast the array of Parcelable objects to the expected type (Array<Track> or Array<Artist>)
        val receivedSearchResultsTyped = receivedSearchResults?.map { it as? Parcelable }?.toTypedArray()

        val receivedSearchFlow = intent.extras?.getSerializable(SearchResultActivity.EXTRA_FLOW)

        // Make assertions to check if the data passed in the intent is correct
        assertArrayEquals(searchResults, receivedSearchResultsTyped)
        assertEquals(SearchFlow.TRACK, receivedSearchFlow)
    }
}

// Example of simple Instrumented test to check the app context
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ksoc.harmonika", appContext.packageName)
    }
}