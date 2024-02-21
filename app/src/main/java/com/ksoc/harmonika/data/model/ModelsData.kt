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
    val artists: List<Artist>,
    val id: String = "",
    val album: Album,
    val disc_number: Int = 0,
    val track_number: Int = 0,
    val duration_ms: Int = 0,
    val explicit: Boolean = false,
    val external_urls: Map<String, String> = mapOf(),
    val href: String = "",
    val uri: String = "",
    val popularity: Int = 0,
    val preview_url: String? = "",
    val is_local: Boolean = false
) : Parcelable

@Parcelize
data class Artist(
    val name: String = "",
    val id: String = "",
    val external_urls: Map<String, String> = mapOf(),
    val href: String = "",
    val uri: String = ""
) : Parcelable

@Parcelize
data class Album(
    val id: String = "",
    val name: String = "",
    val album_type: String = "",
    val total_tracks: Int = 0,
    val release_date: String = "",
    val release_date_precision: String = "",
    val artists: List<Artist> = listOf(),
    val href: String = "",
    val uri: String = "",
    val external_urls: ExternalUrls = ExternalUrls(),
    val images: List<Images> = listOf(),
    val type: String = "",
    val is_playable: Boolean = false
): Parcelable

@Parcelize
data class ExternalUrls(
    val spotify: String = ""
): Parcelable

@Parcelize
data class Images(
    val url: String = "",
    val height: Int = 0,
    val width: Int = 0
): Parcelable


