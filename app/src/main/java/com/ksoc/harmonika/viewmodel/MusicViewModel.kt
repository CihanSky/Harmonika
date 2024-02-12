import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksoc.harmonika.data.MusicRepository
import com.ksoc.harmonika.data.SearchResult
import kotlinx.coroutines.launch

class MusicViewModel(private val repository: MusicRepository) : ViewModel() {
    private val _searchResult = MutableLiveData<SearchResult>()
    val searchResult: LiveData<SearchResult> = _searchResult

    fun searchSongs(query: String) {
        viewModelScope.launch {
            val result = repository.searchSongs(query)
            _searchResult.postValue(result)
        }
    }
}
