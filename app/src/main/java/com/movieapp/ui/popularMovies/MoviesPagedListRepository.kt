package com.movieapp.ui.popularMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.movieapp.Movie
import com.movieapp.api.POST_PER_PAGE
import com.movieapp.api.TheMovieDbInterface
import com.movieapp.repository.MovieDataSourceFactory
import com.movieapp.repository.MoviesDataSource
import com.movieapp.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviesPagedListRepository(private val apiService: TheMovieDbInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()
        return moviePagedList

    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MoviesDataSource, NetworkState>(
            movieDataSourceFactory.moviesLiveDataSource,
            MoviesDataSource::networkState
        )

    }
}