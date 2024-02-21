package com.ksoc.harmonika.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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

