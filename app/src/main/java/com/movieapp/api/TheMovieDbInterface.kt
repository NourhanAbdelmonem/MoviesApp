package com.movieapp.api

import com.movieapp.MovieDetails
import com.movieapp.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbInterface {

    //https://api.themoviedb.org/3/movie/550?api_key=63ee79b141e9be3347beab4251fd2940
    //https://api.themoviedb.org/3/movie/popular?api_key=63ee79b141e9be3347beab4251fd2940
    //https://api.themoviedb.org/3/

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<MoviesResponse>
}