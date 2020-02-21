package com.lukas.awesomemovies.repository

import com.lukas.awesomemovies.data.Constants
import com.lukas.awesomemovies.data.network.MoviesService
import com.lukas.awesomemovies.repository.entity.MovieDetailsEntity
import com.lukas.awesomemovies.repository.entity.MovieEntity
import io.paperdb.Paper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesRepository(private val movieService: MoviesService) {

    fun getTrendingMovies(): Observable<List<MovieEntity>> {
        val observable =
            movieService.getTrendingMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        return observable.map {
            Mapper.mapTrendingMovies(it)
                .sortedByDescending { movieEntity ->
                    movieEntity.popularity
                }
        }
    }

    fun getMovieDetails(movieId: Int): Observable<MovieDetailsEntity> {
        val observable =
            movieService.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        return observable.map {
            Mapper.mapMovieDetails(it)
        }
    }

    fun saveMovieToBookmarks(movie: MovieEntity) {
        val allBookmarks = readAllBookmarks()
        allBookmarks.add(movie)
        Paper.book().write<Set<MovieEntity>>(Constants.BOOKMARKS_FILE, allBookmarks)
    }

    fun readAllBookmarks() =
        Paper.book().read<MutableSet<MovieEntity>>(Constants.BOOKMARKS_FILE, mutableSetOf())
}