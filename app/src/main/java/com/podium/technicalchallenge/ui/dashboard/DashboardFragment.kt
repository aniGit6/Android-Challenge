package com.podium.technicalchallenge.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.viewmodels.MovieViewModel

class DashboardFragment : Fragment() {

    private val viewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                Column(modifier = Modifier.background(colorResource(id = R.color.lavendar))) {
                    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_spacer)))
                    ListBrowsingCategories(view, viewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

@Composable
fun ListBrowsingCategories(view: View?, viewModel: MovieViewModel) {

    Column {
        BrowseMovieCard(R.string.top_five_movies) {
            viewModel.loadMovies(limit = 5)
            viewModel.viewMode = MovieViewModel.ViewMode.TOP_FIVE
            viewModel.genre = null
            view?.findNavController()?.navigate(R.id.action_navigation_dashboard_to_movie_list)
        }
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer).value.dp))
        BrowseMovieCard(R.string.browse_genre) {
            viewModel.viewMode = MovieViewModel.ViewMode.GENRE
            viewModel.loadGenres()
            view?.findNavController()
                ?.navigate(R.id.action_navigation_dashboard_to_genre_list_fragment)
        }
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer).value.dp))
        BrowseMovieCard(R.string.browse_all) {
            viewModel.viewMode = MovieViewModel.ViewMode.ALL
            viewModel.genre = null
            viewModel.loadMovies()
            view?.findNavController()?.navigate(R.id.action_navigation_dashboard_to_movie_list)
        }
    }
}

@Composable
private fun BrowseMovieCard(string_id: Int = 0, clickListener: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 8.dp, end = 8.dp)
        .height(80.dp)
        .clickable { clickListener.invoke() }
    ) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = stringResource(id = string_id),
                    modifier = Modifier.padding(start = 4.dp),
                    fontSize = dimensionResource(id = R.dimen.movie_list_row_label).value.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
