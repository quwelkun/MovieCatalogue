package com.example.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.moviecatalogue.vo.Resource

interface FilmDataSource {
    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getAllTvShows(): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getMovieWithId(movieId: String): LiveData<Resource<MovieEntity>>

    fun getTvShowWithId(tvshowId: String): LiveData<Resource<TvShowEntity>>

    fun setFavMovie(movie: MovieEntity, state: Boolean)

    fun setFavTvShow(tvshow: TvShowEntity, state: Boolean)

    fun getFavMovie(): LiveData<PagedList<MovieEntity>>

    fun getFavTvShow(): LiveData<PagedList<TvShowEntity>>
}