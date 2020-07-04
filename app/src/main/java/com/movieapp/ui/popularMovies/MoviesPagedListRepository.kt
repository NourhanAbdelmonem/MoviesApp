package com.movieapp.ui.popularMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.movieapp.data.valueObject.Movie
import com.movieapp.data.api.POST_PER_PAGE
import com.movieapp.data.api.TheMovieDbInterface
import com.movieapp.data.repository.MovieDataSourceFactory
import com.movieapp.data.repository.MoviesDataSource
import com.movieapp.data.repository.NetworkState
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