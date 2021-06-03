package com.example.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviecatalogue.data.source.local.LocalDataSource
import com.example.moviecatalogue.data.source.local.entity.DetailEntity
import com.example.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.moviecatalogue.data.source.remote.ApiResponse
import com.example.moviecatalogue.data.source.remote.RemoteFilmSource
import com.example.moviecatalogue.data.source.remote.response.DetailResponse
import com.example.moviecatalogue.data.source.remote.response.MovieResponse
import com.example.moviecatalogue.data.source.remote.response.TvShowResponse
import com.example.moviecatalogue.utils.AppExecutors
import com.example.moviecatalogue.vo.Resource

class FilmRepository private constructor(
    private val remoteFilm: RemoteFilmSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : FilmDataSource {

    companion object {
        @Volatile
        private var instance: FilmRepository? = null

        fun getInstance(
            remoteFilm: RemoteFilmSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): FilmRepository =
            instance ?: synchronized(this) {
                instance ?: FilmRepository(
                    remoteFilm,
                    localDataSource,
                    appExecutors
                ).apply { instance = this }
            }
    }

    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteFilm.getAllMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                val moviesList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        id = response.id,
                        title = response.title,
                        duration = response.duration,
                        poster = response.poster,
                        overview = response.overview,
                        type = response.type,
                        genre = response.genre,
                        fav = response.fav
                    )
                    moviesList.add(movie)
                }

                localDataSource.insertMovies(moviesList)
            }
        }.asLiveData()
    }

    override fun getAllTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                remoteFilm.getAllTvShows()

            override fun saveCallResult(data: List<TvShowResponse>) {
                val tvshowsList = ArrayList<TvShowEntity>()
                for (response in data) {
                    val movie = TvShowEntity(
                        id = response.id,
                        title = response.title,
                        duration = response.duration,
                        poster = response.poster,
                        overview = response.overview,
                        type = response.type,
                        genre = response.genre,
                        fav = response.fav
                    )
                    tvshowsList.add(movie)
                }
                localDataSource.insertTvShow(tvshowsList)
            }
        }.asLiveData()
    }

    override fun getMovieWithId(movieId: String): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, DetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getMovieWithId(movieId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data != null && data.genre == ""
            }

            override fun createCall(): LiveData<ApiResponse<DetailResponse>> {
                return remoteFilm.getMovieWithId(movieId)
            }

            override fun saveCallResult(data: DetailResponse) {
                val movieEntity = DetailEntity(
                    id = data.id,
                    poster = data.poster,
                    overview = data.overview,
                    duration = data.duration,
                    title = data.title,
                    type = data.type,
                    genre = data.genre,
                    fav = data.fav
                )

                localDataSource.updateMovie(movieEntity, false)
            }
        }.asLiveData()
    }

    override fun getTvShowWithId(tvshowId: String): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, DetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowEntity> {
                return localDataSource.getTvShowWithId(tvshowId)
            }

            override fun shouldFetch(data: TvShowEntity?): Boolean {
                return data != null && data.genre == ""
            }

            override fun createCall(): LiveData<ApiResponse<DetailResponse>> {
                return remoteFilm.getTvShowWithId(tvshowId)
            }

            override fun saveCallResult(data: DetailResponse) {
                val tvShowEntity = DetailEntity(
                    id = data.id,
                    poster = data.poster,
                    overview = data.overview,
                    duration = data.duration,
                    title = data.title,
                    type = data.type,
                    genre = data.genre,
                    fav = data.fav
                )

                localDataSource.updateTvShow(tvShowEntity, false)
            }
        }.asLiveData()
    }

    override fun setFavMovie(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movie, state)
        }
    }

    override fun setFavTvShow(tvshow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteTvShow(tvshow, state)
        }
    }

    override fun getFavMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavMovie(), config).build()
    }

    override fun getFavTvShow(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavTvShow(), config).build()
    }


}
