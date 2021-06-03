package com.example.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.data.source.local.entity.TvShowEntity

@Dao
interface FIlmDao {

    @Query("SELECT * FROM movie_entities")
    fun getAllMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie_entities WHERE id = :id")
    fun getMovieWithId(id: String): LiveData<MovieEntity>

    @Query("SELECT * FROM movie_entities WHERE fav = 1")
    fun getFavMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvshow_entities")
    fun getAllTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tvshow_entities WHERE id = :id")
    fun getTvShowWithId(id: String): LiveData<TvShowEntity>

    @Query("SELECT * FROM tvshow_entities WHERE fav = 1")
    fun getFavTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)
}