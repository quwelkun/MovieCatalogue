package com.example.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.moviecatalogue.data.source.local.entity.DetailEntity
import com.example.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.moviecatalogue.data.source.local.room.FIlmDao

class LocalDataSource private constructor(private val mFilmDao: FIlmDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(fIlmDao: FIlmDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(fIlmDao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = mFilmDao.getAllMovies()

    fun getFavMovie(): DataSource.Factory<Int, MovieEntity> = mFilmDao.getFavMovie()

    fun getAllTvShows(): DataSource.Factory<Int, TvShowEntity> = mFilmDao.getAllTvShows()

    fun getFavTvShow(): DataSource.Factory<Int, TvShowEntity> = mFilmDao.getFavTvShow()

    fun getMovieWithId(id: String): LiveData<MovieEntity> = mFilmDao.getMovieWithId(id)

    fun getTvShowWithId(id: String): LiveData<TvShowEntity> = mFilmDao.getTvShowWithId(id)

    fun insertMovies(movie: List<MovieEntity>) = mFilmDao.insertMovies(movie)

    fun insertTvShow(tvshow: List<TvShowEntity>) = mFilmDao.insertTvShows(tvshow)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.fav = newState
        mFilmDao.updateMovie(movie)
    }

    fun updateMovie(detailMovie: DetailEntity, newState: Boolean) {
        val movie = MovieEntity(
            id = detailMovie.id,
            genre = detailMovie.genre,
            overview = detailMovie.overview,
            duration = detailMovie.duration,
            type = detailMovie.type,
            title = detailMovie.title,
            fav = newState,
            poster = detailMovie.poster
        )
        mFilmDao.updateMovie(movie)
    }

    fun setFavoriteTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.fav = newState
        mFilmDao.updateTvShow(tvShow)
    }

    fun updateTvShow(detailMovie: DetailEntity, newState: Boolean) {
        val tvShow = TvShowEntity(
            id = detailMovie.id,
            genre = detailMovie.genre,
            overview = detailMovie.overview,
            duration = detailMovie.duration,
            type = detailMovie.type,
            title = detailMovie.title,
            fav = newState,
            poster = detailMovie.poster
        )
        mFilmDao.updateTvShow(tvShow)

    }
}