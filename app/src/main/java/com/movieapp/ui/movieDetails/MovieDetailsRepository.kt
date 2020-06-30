package com.movieapp.ui.movieDetails

import androidx.lifecycle.LiveData
import com.movieapp.MovieDetails
import com.movieapp.api.TheMovieDbInterface
import com.movieapp.repository.MovieDetailsNetworkDataSource
import com.movieapp.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService:TheMovieDbInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource
    fun fetchMovieDetails(compositeDisposable: CompositeDisposable,movieId:Int):LiveData<MovieDetails>
    {
        movieDetailsNetworkDataSource= MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadMovieDetailsResponse
    }


    fun getMovieDetailsNetworkState():LiveData<NetworkState>
    {
        return movieDetailsNetworkDataSource.networkState
    }
}