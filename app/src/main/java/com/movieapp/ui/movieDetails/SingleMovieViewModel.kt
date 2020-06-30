package com.movieapp.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.movieapp.MovieDetails
import com.movieapp.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieDetailsRepository: MovieDetailsRepository, movieId :Int) :ViewModel() {

    private val compositeDisposable=CompositeDisposable()
    val movieDetails:LiveData<MovieDetails> by lazy {
        movieDetailsRepository.fetchMovieDetails(compositeDisposable,movieId)
    }

    val networkState:LiveData<NetworkState>by lazy {
        movieDetailsRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}