package com.movieapp.ui.movieDetails

import androidx.lifecycle.LiveData
import com.movieapp.data.valueObject.MovieDetails
import com.movieapp.data.api.TheMovieDbInterface
import com.movieapp.data.repository.MovieDetailsNetworkDataSource
import com.movieapp.data.repository.NetworkState
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