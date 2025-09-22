package fr.eseo.ld.ts.movieposters.repositories

import fr.eseo.ld.ts.movieposters.models.Movie
import fr.eseo.ld.ts.movieposters.network.OmdbApiService

class OmdbRepository (private val apiService: OmdbApiService) {
    suspend fun fetchMovie(title: String, apiKey: String): Movie {
        return apiService.fetchMovie(title, apiKey)
    }
}
