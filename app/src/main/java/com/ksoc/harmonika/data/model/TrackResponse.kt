package com.ksoc.harmonika.data.model

data class TrackResponse(
    val tracks: TrackItems,
) {
    data class TrackItems(
        val items: List<Track>,
        val limit: Int,
        val offset: Int,
        val total: Int
    )
}