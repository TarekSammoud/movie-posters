package fr.eseo.ld.ts.movieposters.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Released") val released: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Director") val director: String
)