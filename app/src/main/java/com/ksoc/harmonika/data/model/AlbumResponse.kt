package com.ksoc.harmonika.data.model

data class AlbumResponse(
    val albums: Albums,
) {
    data class Albums(
        val href: String,
        val items: List<Album>,
        val limit: Int,
        val next: String?,
        val offset: Int,
        val previous: String?,
        val total: Int
    )
}
