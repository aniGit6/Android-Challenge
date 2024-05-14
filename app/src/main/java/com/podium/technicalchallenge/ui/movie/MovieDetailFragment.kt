package com.podium.technicalchallenge.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import coil.compose.AsyncImage
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.viewmodels.MovieViewModel

class MovieDetailFragment : Fragment() {

    private val viewModel: MovieViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /*
    * Include title, rating, genres, poster, and description
    List the cast
    List the director
* */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                RenderMovieDetails(viewModel)
            }
        }
    }
}

@Composable
fun RenderMovieDetails(viewModel: MovieViewModel) {

    Column {
        viewModel.movie.value?.let { movie ->
            Text(text = movie.title) // Title
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.movie_spacer)))
            Text(text = movie.title) // Rating
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.movie_spacer)))
            Text(text = movie.genresAsString()) // Genres
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.movie_spacer)))
            // Poster
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.movie_spacer)))
            AsyncImage(model = movie.posterPath, contentDescription = "Movie Poster")
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.movie_spacer)))
            Text(text = movie.overview ?: "") // Description

            LazyRow {

            }
        } ?: run {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }
    }
}