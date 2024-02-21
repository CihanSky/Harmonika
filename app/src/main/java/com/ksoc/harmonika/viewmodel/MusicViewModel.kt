
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksoc.harmonika.data.model.ArtistItem
import com.ksoc.harmonika.data.model.TrackItem
import com.ksoc.harmonika.data.repository.MusicRepository
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {
    private val trackRepository = MusicRepository()

    // Using LiveData to hold the search results
    private val _searchResults = MutableLiveData<List<TrackItem>>()
    val searchResults: LiveData<List<TrackItem>> = _searchResults

    private val _searchArtists = MutableLiveData<List<ArtistItem>>()
    val searchArtists: LiveData<List<ArtistItem>> = _searchArtists

    fun searchTracks(query: String, callback: (List<TrackItem>) -> Unit) {
        viewModelScope.launch {
            val tracks = trackRepository.searchSongs(query) ?: listOf()
            _searchResults.value = tracks

            // Invoke the callback with the search results
            callback(tracks)
        }
    }

    fun searchArtists(query: String, callback: (List<ArtistItem>) -> Unit) {
        viewModelScope.launch {
            val artists = trackRepository.searchArtists(query) ?: listOf()
            _searchArtists.value = artists

            // Invoke the callback with the search results
            callback(artists)
        }
    }
}