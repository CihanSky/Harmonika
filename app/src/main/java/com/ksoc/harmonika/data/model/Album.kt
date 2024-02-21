package com.ksoc.harmonika.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
