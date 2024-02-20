
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksoc.harmonika.data.repository.MusicRepository
import com.ksoc.harmonika.data.model.TrackItem
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {
    private val trackRepository = MusicRepository()

    var searchResults by mutableStateOf<List<TrackItem>?>(null)
        private set

    fun searchTracks(query: String) {
        viewModelScope.launch {
            val tracks = trackRepository.searchSongs(query) ?: listOf()
            searchResults = tracks
        }
    }
}
