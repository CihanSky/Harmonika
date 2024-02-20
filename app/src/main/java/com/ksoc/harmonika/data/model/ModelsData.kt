package com.ksoc.harmonika.data.model

data class SearchResponse(
    val tracks: TrackResponse
)

data class TrackResponse(
    val items: List<TrackItem>
)

data class TrackItem(
    val name: String,
    val artists: List<Artist>
)

data class Artist(
    val name: String
)
