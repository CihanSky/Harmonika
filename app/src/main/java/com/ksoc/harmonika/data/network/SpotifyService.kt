package com.ksoc.harmonika.data.network

import com.ksoc.harmonika.data.model.AlbumResponse
import com.ksoc.harmonika.data.model.ArtistResponse
import com.ksoc.harmonika.data.model.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyApiService {
    @GET("search")
    suspend fun searchTracks(
        @Header("Authorization") accessToken: String,
        @Query("q") query: String,
        @Query("type") type: String = "track"
    ): TrackResponse

    @GET("search")
    suspend fun searchArtists(
        @Header("Authorization") accessToken: String,
        @Query("q") query: String,
        @Query("type") type: String = "artist"
    ): ArtistResponse

    @GET("search")
    suspend fun searchAlbums(
        @Header("Authorization") accessToken: String,
        @Query("q") query: String,
        @Query("type") type: String = "album"
    ): AlbumResponse
}
