package com.movieapp.ui.movieDetails

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.movieapp.MovieDetails
import com.movieapp.R
import com.movieapp.api.POSTER_BASE_URL
import com.movieapp.api.TheMovieDBClient
import com.movieapp.api.TheMovieDbInterface
import com.movieapp.repository.NetworkState
import kotlinx.android.synthetic.main.activity_details.*
import java.text.NumberFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {


    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val movieId: Int = intent.getIntExtra("id", 1)
        val apiService: TheMovieDbInterface = TheMovieDBClient.getClient()
        movieDetailsRepository =
            MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    @SuppressLint("SetTextI18n")
    fun bindUI(it: MovieDetails) {
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)

    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(
                    movieDetailsRepository,
                    movieId
                ) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}
