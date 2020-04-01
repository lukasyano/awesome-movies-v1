package com.lukas.awesomemovies.data.network

import com.lukas.awesomemovies.data.network.model.MovieDetailsResponse
import com.lukas.awesomemovies.data.network.model.MoviesResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("trending/movie/week")
    fun getTrendingMovies(@Query("pageNr") pageNr: Int) : Observable<MoviesResponse>

    @GET("movie/{movieId}")
    fun getMovieDetails(@Path("movieId") movieId : Int) : Single<MovieDetailsResponse>

    @GET("search/movie")
    fun getMoviesFromSearch(@Query("query") query: String) : Single<MoviesResponse>

}
