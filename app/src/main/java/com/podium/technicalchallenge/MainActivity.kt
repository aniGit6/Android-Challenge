package com.podium.technicalchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.podium.technicalchallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            runOnUiThread {
                if (destination.id == R.id.mobile_navigation) {
                    destination.label = getString(R.string.movie_overview)
                } else if (destination.id == R.id.movie_detail_fragment) {
                    destination.label = getString(R.string.movie_details)
                } else if (destination.id == R.id.genre_list_fragment) {
                    destination.label = getString(R.string.browse_genre)
                } else if (destination.id == R.id.movie_list_fragment) {
                    val genre = arguments?.getString("genre")
                    if (genre != null) {
                        destination.label = getString(R.string.genre_movies, genre)
                    } else {
                        destination.label = getString(R.string.movies)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}