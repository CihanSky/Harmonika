
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksoc.harmonika.data.MusicRepository
import com.ksoc.harmonika.data.TrackItem
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {
    private val trackRepository = MusicRepository()

//    private val _searchResults = MutableLiveData<List<TrackItem>?>()
//    val searchResults: LiveData<List<TrackItem>?> = _searchResults
//
//    fun searchTracks(query: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val tracks = trackRepository.searchSongs(query) ?: listOf()
//            _searchResults.postValue(tracks)
//        }
//    }

    var searchResults by mutableStateOf<List<TrackItem>?>(null)
        private set

    fun searchTracks(query: String) {
        viewModelScope.launch {
            val tracks = trackRepository.searchSongs(query) ?: listOf()
            searchResults = tracks
        }
    }
}
