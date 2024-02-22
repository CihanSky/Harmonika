package com.ksoc.harmonika.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    val name: String = "",
    val id: String = "",
    val external_urls: Map<String, String> = mapOf(),
    val href: String = "",
    val uri: String = "",
    val images: List<Image> = listOf(),
    val genres: List<String>? = listOf(),
) : Parcelable

@Parcelize
data class Image(
    val height: Int,
    val url: String,
    val width: Int
):Parcelable