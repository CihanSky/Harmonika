package com.ksoc.harmonika.data

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyApiService {
    @GET("search")
    suspend fun searchTracks(
        @Header("Authorization") accessToken: String,
        @Query("q") query: String,
        @Query("type") type: String = "track"
    ): SearchResponse
}
