package com.podium.technicalchallenge

import android.util.Log
import com.google.gson.Gson
import com.podium.technicalchallenge.entity.GenreResponse
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.entity.MovieDetailEntity
import com.podium.technicalchallenge.entity.MovieDetailResponse
import com.podium.technicalchallenge.entity.MovieResponse
import com.podium.technicalchallenge.network.ApiClient
import com.podium.technicalchallenge.network.queries.Queries
import com.podium.technicalchallenge.network.retrofit.GraphQLService
import org.json.JSONObject


class Repo {

    suspend fun getMovies(limit: Int = LIMIT, offset: Int, order: String = "title", sort: Queries.MOVIE_SORT_DIRECTION = Queries.MOVIE_SORT_DIRECTION.ASC): List<Movie> {
        val paramObject = JSONObject()
        paramObject.put("query", Queries.getMoviesPagingQuery(limit, offset, order, sort))

        val response =
            ApiClient.getInstance().provideRetrofitClient().create(GraphQLService::class.java)
                .query(paramObject.toString())
        val jsonBody = response.body()
        val data = Gson().fromJson(jsonBody, MovieResponse::class.java)
        return data.data.movies
    }

    suspend fun getMoviesForGenre(limit: Int = LIMIT, offset: Int, genre: String): List<Movie> {
        val paramObject = JSONObject()
        val query = Queries.getMoviesByGenre(limit, offset, genre)
        paramObject.put("query", query)

        val response =
            ApiClient.getInstance().provideRetrofitClient().create(GraphQLService::class.java)
                .query(paramObject.toString())
        val jsonBody = response.body()
        val data = Gson().fromJson(jsonBody, MovieResponse::class.java)
        return data.data.movies
    }

    suspend fun getGenres(): List<String> {
        val paramObject = JSONObject()
        paramObject.put("query", Queries.getGenres())

        val response =
            ApiClient.getInstance().provideRetrofitClient().create(GraphQLService::class.java)
                .query(paramObject.toString())
        val jsonBody = response.body()

        val data = Gson().fromJson(jsonBody, GenreResponse::class.java)
        return data.data.genres
    }

    suspend fun getMovieDetails(id: Int): MovieDetailEntity {
        val paramObject = JSONObject()
        paramObject.put("query", Queries.getMovieDetails(id))

        val response =
            ApiClient.getInstance().provideRetrofitClient().create(GraphQLService::class.java)
                .query(paramObject.toString())
        val jsonBody = response.body()
        val data = Gson().fromJson(jsonBody, MovieDetailResponse::class.java)
        return data.data.movie
    }

    companion object {
        private var INSTANCE: Repo? = null
        val LIMIT: Int = 20
        fun getInstance() = INSTANCE
            ?: Repo().also {
                INSTANCE = it
            }
    }
}
