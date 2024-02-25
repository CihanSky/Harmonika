package com.ksoc.harmonika.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksoc.harmonika.data.model.Album
import com.ksoc.harmonika.data.model.Artist
import com.ksoc.harmonika.data.model.Track
import com.ksoc.harmonika.data.repository.MusicRepository
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {
    private val trackRepository = MusicRepository()

    private val _searchResults = MutableLiveData<List<Track>>()
    val searchResults: LiveData<List<Track>> = _searchResults

    private val _searchArtists = MutableLiveData<List<Artist>>()
    val searchArtists: LiveData<List<Artist>> = _searchArtists

    private val _searchAlbums = MutableLiveData<List<Album>>()
    val searchAlbums: LiveData<List<Album>> = _searchAlbums

    fun searchTracks(query: String, callback: (List<Track>) -> Unit) {
        viewModelScope.launch {
            val tracks = trackRepository.searchTracks(query)
            _searchResults.value = tracks
            callback(tracks)
        }
    }

    fun searchArtists(query: String, callback: (List<Artist>) -> Unit) {
        viewModelScope.launch {
            val artists = trackRepository.searchArtists(query)
            _searchArtists.value = artists
            callback(artists)
        }
    }

    fun searchAlbums(query: String, callback: (List<Album>) -> Unit) {
        viewModelScope.launch {
            val albums = trackRepository.searchAlbums(query)
            _searchAlbums.value = albums
            callback(albums)
        }
    }
}