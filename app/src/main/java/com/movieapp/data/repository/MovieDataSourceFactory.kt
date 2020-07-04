package com.movieapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.movieapp.data.valueObject.Movie
import com.movieapp.data.api.TheMovieDbInterface
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: TheMovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val moviesDataSource = MoviesDataSource(apiService, compositeDisposable)
        moviesLiveDataSource.postValue(moviesDataSource)

        return moviesDataSource
    }
}