package com.podium.technicalchallenge.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.Repo
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.entity.MovieDetailEntity
import com.podium.technicalchallenge.network.queries.Queries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    var movies = mutableStateListOf<Movie>()
    var genres = mutableStateListOf<String>()
    val state: State = State()
    var movie = mutableStateOf<MovieDetailEntity?>(null)
    var responseSize : Int = Repo.LIMIT
    var viewMode : ViewMode = ViewMode.ALL
    var genre: String? = null

    enum class ViewMode {
        TOP_FIVE,
        GENRE,
        ALL
    }

    fun loadMovies(limit: Int = Repo.LIMIT, offset: Int = 0, genre: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            this@MovieViewModel.state.isLoading = true

            if (offset == 0) {
                this@MovieViewModel.movies.clear()
            }
            if (limit == 5) {
                this@MovieViewModel.movies.addAll(Repo.getInstance().getMovies(limit, offset, order = "popularity", sort = Queries.MOVIE_SORT_DIRECTION.DESC))
            }
            else if (genre != null) {
                val movies = Repo.getInstance().getMoviesForGenre(limit, offset, genre).toMutableStateList()
                this@MovieViewModel.responseSize = movies.count()
                this@MovieViewModel.movies.addAll(
                    movies
                )
            } else {
                val movies = Repo.getInstance().getMovies(limit, offset)
                this@MovieViewModel.responseSize = movies.count()
                this@MovieViewModel.movies.addAll(movies)
            }
            this@MovieViewModel.state.isLoading = false
        }
    }

    fun loadGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            this@MovieViewModel.state.isLoading = true
            this@MovieViewModel.genres.clear()
            this@MovieViewModel.genres.addAll(Repo.getInstance().getGenres().toMutableStateList())
            this@MovieViewModel.state.isLoading = false
        }
    }

    fun getMovieDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            this@MovieViewModel.state.isLoading = true
            this@MovieViewModel.movie.value = null
            this@MovieViewModel.movie.value = Repo.getInstance().getMovieDetails(id)
            this@MovieViewModel.state.isLoading = false
        }
    }

    inner class State(var isLoading: Boolean = false) {
        fun endReached(): Boolean {
            return ((this@MovieViewModel.viewMode == ViewMode.ALL || this@MovieViewModel.viewMode  == ViewMode.GENRE) && responseSize >= Repo.LIMIT)
        }
    }
}

