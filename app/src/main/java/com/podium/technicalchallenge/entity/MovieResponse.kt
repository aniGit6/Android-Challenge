package com.podium.technicalchallenge.entity

import androidx.compose.runtime.mutableStateOf

data class MovieResponse(
    val data: Movies
)

data class Movies(
    val movies: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
)

data class MovieDetailResponse(
   val data: MovieDetail
)
data class MovieDetail(
    val movie: MovieDetailEntity
)
data class MovieDetailEntity(
    val title: String,
    val overview: String,
    val posterPath: String,
    val popularity: String,
    val genres: List<String>,
    val director: Director,
    val cast: List<CastEntity>
) {
    fun genresAsString(): String {
        return this.genres.joinToString(", ").trim()
    }
}

data class CastEntity(
    val profilePath: String,
    val name: String,
    val character: String,
    val order: Int
)

data class Director(
    val id: Int,
    val name: String
)

data class GenreResponse(
    val data: Genres
)

data class Genres(
    val genres: List<String>
)