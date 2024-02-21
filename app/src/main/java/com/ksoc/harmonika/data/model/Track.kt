package com.ksoc.harmonika.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
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
): Parcelable
