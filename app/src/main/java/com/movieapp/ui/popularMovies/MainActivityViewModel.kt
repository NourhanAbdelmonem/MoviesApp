package com.movieapp.ui.popularMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.movieapp.Movie
import com.movieapp.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movierepository: MoviesPagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        movierepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movierepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}