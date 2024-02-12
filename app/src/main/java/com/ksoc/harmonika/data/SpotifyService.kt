package com.ksoc.harmonika.data

import retrofit2.http.GET
import retrofit2.http.Query

interface SpotifyService {
    @GET("search")
    suspend fun searchSongs(@Query("q") query: String): SearchResult
}
