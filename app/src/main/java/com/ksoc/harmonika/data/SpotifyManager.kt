
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.IOException

interface SpotifyAuthService {
    @FormUrlEncoded
    @POST("api/token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String
    ): AccessTokenResponse
}

data class AccessTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Long
)

// Retrofit instance for authentication
val retrofitAuth = Retrofit.Builder()
    .baseUrl("https://accounts.spotify.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val spotifyAuthService = retrofitAuth.create(SpotifyAuthService::class.java)

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

interface SpotifyApiService {
    @GET("search")
    suspend fun searchTracks(
        @Header("Authorization") accessToken: String,
        @Query("q") query: String,
        @Query("type") type: String = "track"
    ): SearchResponse
}

data class SearchResponse(
    val tracks: TrackResponse
)

data class TrackResponse(
    val items: List<TrackItem>
)

data class TrackItem(
    val name: String,
    val artists: List<Artist>
)

data class Artist(
    val name: String
)

// Retrofit instance for API calls
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.spotify.com/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val spotifyApiService = retrofit.create(SpotifyApiService::class.java)

// Function to perform the search
suspend fun searchSongs(query: String): List<TrackItem>? {
    val accessToken = getAccessToken()
    try {
        val response = spotifyApiService.searchTracks("Bearer $accessToken", query)
        return response.tracks.items
    } catch (e: Exception) {
        throw IOException("Error searching for tracks: ${e.message}")
    }
}



//    val clientId = "7b9b98118a7e481bbc273ba6e1cf2260"
//    val clientSecret = "f8d463967fe24cf3a8b267354b39674c"
