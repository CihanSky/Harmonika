package com.ksoc.harmonika.data

class MusicRepository(private val spotifyService: SpotifyService) {
    suspend fun searchSongs(query: String): SearchResult {
        return spotifyService.searchSongs(query)
    }
}