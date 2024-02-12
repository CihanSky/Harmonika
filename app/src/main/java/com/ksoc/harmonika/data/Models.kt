package com.ksoc.harmonika.data

data class Song(val id: String, val name: String, val artist: String, val album: String)

data class SearchResult(val songs: List<Song>)
