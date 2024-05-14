package com.podium.technicalchallenge.entity

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

    fun castAsString(): String {
        val list = mutableListOf<String>()
        this.cast.forEach {castMember ->
            list.add(castMember.name)
        }
        return list.joinToString(", ").trim()
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