
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksoc.harmonika.data.model.Artist
import com.ksoc.harmonika.data.model.Track
import com.ksoc.harmonika.data.repository.MusicRepository
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {
    private val trackRepository = MusicRepository()

    // Using LiveData to hold the search results
    private val _searchResults = MutableLiveData<List<Track>>()
    val searchResults: LiveData<List<Track>> = _searchResults

    private val _searchArtists = MutableLiveData<List<Artist>>()
    val searchArtists: LiveData<List<Artist>> = _searchArtists

    fun searchTracks(query: String, callback: (List<Track>) -> Unit) {
        viewModelScope.launch {
            val tracks = trackRepository.searchTracks(query)
            _searchResults.value = tracks

            // Invoke the callback with the search results
            callback(tracks)
        }
    }

    fun searchArtists(query: String, callback: (List<Artist>) -> Unit) {
        viewModelScope.launch {
            val artists = trackRepository.searchArtists(query) ?: listOf()
            _searchArtists.value = artists

            // Invoke the callback with the search results
            callback(artists)
        }
    }
}