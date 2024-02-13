package com.ksoc.harmonika.data

import java.io.IOException

class MusicRepository {

    // Function to perform the search
    suspend fun searchSongs(query: String): List<TrackItem>? {
        val accessToken = getAccessToken()
        try {
            val response = RetrofitClient.spotifyApiService
                .searchTracks("Bearer $accessToken", query)
            return response.tracks.items
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