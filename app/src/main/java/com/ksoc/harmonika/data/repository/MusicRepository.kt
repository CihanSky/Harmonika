package com.ksoc.harmonika.data.repository

import com.ksoc.harmonika.data.model.Artist
import com.ksoc.harmonika.data.model.Track
import com.ksoc.harmonika.data.network.RetrofitClient
import com.ksoc.harmonika.data.network.spotifyAuthService
import java.io.IOException

class MusicRepository {

    suspend fun searchTracks(query: String): List<Track> {
        val accessToken = getAccessToken()
        try {
            val response = RetrofitClient.spotifyApiService.searchTracks("Bearer $accessToken", query)
            return response.tracks.items
        } catch (e: Exception) {
            throw IOException("Error searching for tracks: ${e.message}")
        }
    }

    suspend fun searchArtists(query: String): List<Artist> {
        val accessToken = getAccessToken()
        try {
            val response = RetrofitClient.spotifyApiService.searchArtists("Bearer $accessToken", query)
            return response.artists.items
        } catch (e: Exception) {
            throw IOException("Error searching for tracks: ${e.message}")
        }
    }


    suspend fun getAccessToken(): String {
        val clientId = "7b9b98118a7e481bbc273ba6e1cf2260"
        val clientSecret = "f8d463967fe24cf3a8b267354b39674c"
        val grantType = "client_credentials"

        return try {
            val response = spotifyAuthService.getAccessToken(clientId, clientSecret, grantType)
            response.accessToken
        } catch (e: Exception) {
            throw IOException("Error getting access token: ${e.message}")
        }
    }
}