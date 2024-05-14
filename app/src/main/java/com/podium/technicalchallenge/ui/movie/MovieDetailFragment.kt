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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_spacer)))
        viewModel.movie.value?.let { movie ->
            // Poster
            AsyncImage(model = movie.posterPath, contentDescription = "Movie Poster")
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_spacer)))

            // Title
            LabelText(stringId = R.string.title)
            ItemText(string = movie.title)
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_spacer)))
            // Rating
            LabelText(stringId = R.string.rating)
            ItemText(string = movie.popularity)
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_spacer)))
            // Genres
            LabelText(stringId = R.string.genres)
            ItemText(string = movie.genresAsString())
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_spacer)))

            // Description
            LabelText(stringId = R.string.description)
            ItemText(string = movie.castAsString())

            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_spacer)))
            LabelText(stringId = R.string.cast)
            Row {
                ItemText(string = movie.castAsString())
            }

            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_spacer)))
            LabelText(stringId = R.string.director)
            ItemText(string = movie.director.name)

        } ?: run {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun LabelText(stringId: Int) {
    Text(
        text = stringResource(id = stringId),
        fontSize = dimensionResource(id = R.dimen.movie_detail_label_font_size).value.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun ItemText(string: String) {
    Text(
        text = string,
        fontSize = dimensionResource(id = R.dimen.movie_detail_item_font_size).value.sp
    )
}