package com.podium.technicalchallenge.ui.genre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.ui.movie.LoadingIndicator
import com.podium.technicalchallenge.viewmodels.MovieViewModel

class GenreListFragment : Fragment() {
    private val viewModel : MovieViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ListOfGenres(view, viewModel)
            }
        }
    }
}

@Composable
fun ListOfGenres(view: View?, viewModel: MovieViewModel) {
   Column {
       Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_spacer)))
       LazyColumn {
           items(viewModel.genres) { genre ->
               Column(
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.Start,
                   modifier = Modifier
                       .padding(start = 4.dp, end = 4.dp)
                       .clickable {
                           viewModel.viewMode = MovieViewModel.ViewMode.GENRE
                           viewModel.genre = genre
                           viewModel.loadMovies(genre = genre)
                           val bundle = bundleOf("genre" to genre)
                           view
                               ?.findNavController()
                               ?.navigate(R.id.action_genre_list_fragment_to_movie_list_fragment, bundle)
                       },
               ) {
                   Text(
                       text = genre,
                       fontSize = dimensionResource(id = R.dimen.movie_list_row_label).value.sp
                   )
                   Spacer(modifier = Modifier.size(8.dp))
                   Divider(modifier = Modifier.background(Color.Gray), thickness = 1.dp)
               }
           }
           item {
               LoadingIndicator(viewModel)
           }
       }
    }
}

