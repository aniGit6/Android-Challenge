package com.podium.technicalchallenge.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.viewmodels.MovieViewModel

class MovieListFragment : Fragment() {
    private val viewModel: MovieViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ListOfMovies(view, viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.overflow_sort_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                val genre = viewModel.genre
                when (menuItem.itemId) {
                    R.id.action_title -> {
                        if (genre != null) {
                            viewModel.loadMovies(genre = genre, order = "title")
                        } else {
                            viewModel.loadMovies(order = "title")
                        }

                        return true
                    }

                    R.id.action_popularity -> {
                        if (genre != null) {
                            viewModel.loadMovies(genre = genre, order = "popularity")
                        } else {
                            viewModel.loadMovies(order = "popularity")
                        }

                        return true
                    }

                    R.id.action_runtime -> {
                        if (genre != null) {
                            viewModel.loadMovies(genre = genre, order = "runtime")
                        } else {
                            viewModel.loadMovies(order = "runtime")
                        }

                        return true
                    }
                }
                return false
            }
        }, viewLifecycleOwner)
    }
}

@Composable
fun ListOfMovies(view: View?, viewModel: MovieViewModel) {
    Column(modifier = Modifier.background(colorResource(id = R.color.lavendar))) {
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer)))
        LazyColumn {
            items(viewModel.movies.size) { i ->
                val movie = viewModel.movies.get(i)
                if (viewModel.state.endReached()) {
                    viewModel.loadMovies(
                        offset = viewModel.movies.size - 1,
                        genre = viewModel.genre
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .background(colorResource(id = R.color.lavendar))
                        .clickable {
                            viewModel.getMovieDetails(movie.id)
                            view
                                ?.findNavController()
                                ?.navigate(R.id.action_movie_list_fragment_to_movieDetailFragment)
                        }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = movie.title,
                                fontSize = dimensionResource(id = R.dimen.movie_list_row_item).value.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
            item {
                LoadingIndicator(viewModel)
            }
        }
    }
}

@Composable
fun LoadingIndicator(viewModel: MovieViewModel) {
    if (viewModel.state.isLoading) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
}