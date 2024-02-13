package com.ksoc.harmonika.data

import AccessTokenResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SpotifyAuthService {
    @FormUrlEncoded
    @POST("api/token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String
    ): AccessTokenResponse
}

// Retrofit instance for authentication
val retrofitAuth = Retrofit.Builder()
    .baseUrl("https://accounts.spotify.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val spotifyAuthService = retrofitAuth.create(SpotifyAuthService::class.java)