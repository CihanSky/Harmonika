package com.ksoc.harmonika.data.model

data class ArtistResponse(
    val artists: Artists,
) {
    data class Artists(
        val href: String,
        val items: List<Artist>,
        val limit: Int,
        val next: String?,
        val offset: Int,
        val previous: String?,
        val total: Int
    )
}
