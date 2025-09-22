package fr.eseo.ld.ts.movieposters.network

import fr.eseo.ld.ts.movieposters.models.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {

    @GET("/")
    suspend fun fetchMovie(
        @Query("t") title: String,
        @Query("apikey") apiKey: String
    ) : Movie;

}

object OmdbApiServiceImpl {
    private const val BASE_URL = "https://www.omdbapi.com/"
    val api : OmdbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmdbApiService::class.java)
    }
}
