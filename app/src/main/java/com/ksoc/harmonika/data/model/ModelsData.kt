package com.ksoc.harmonika.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class SearchResponse(
    val tracks: TrackResponse
)

data class TrackResponse(
    val items: List<TrackItem>
)

@Parcelize
data class TrackItem(
    val name: String,
    val artists: List<Artist>
): Parcelable

@Parcelize
data class Artist(
    val name: String
): Parcelable
