package com.example.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviecatalogue.data.source.local.LocalDataSource
import com.example.moviecatalogue.data.source.local.entity.DetailEntity
import com.example.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.moviecatalogue.data.source.remote.RemoteFilmSource
import com.example.moviecatalogue.utils.AppExecutors
import com.example.moviecatalogue.utils.DummyFilm
import com.example.moviecatalogue.utils.LiveDataTestUtil
import com.example.moviecatalogue.utils.PagedListUtil
import com.example.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class FilmRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteFilmSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val filmRepository = FakeFilmRepository(remote, local, appExecutors)

    private val moviesResponse = DummyFilm.getRemoteMovies()
    private val movieId = moviesResponse[0].id.toString()
    private val movieDetail = DummyFilm.getRemoteDetailMovies()

    private val tvShowResponse = DummyFilm.getRemoteTvShows()
    private val tvShowDetail = DummyFilm.getRemoteDetailTvShows()
    private val tvShowId = tvShowDetail.id.toString()

    @Test
    fun getMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        filmRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DummyFilm.getMovies()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities)
        assertEquals(moviesResponse.size, movieEntities.data?.size)
    }

    @Test
    fun getDetailMovie() {
        val dummyDetail = MutableLiveData<MovieEntity>()
        dummyDetail.value = DummyFilm.getDetailMovies()
        `when`(local.getMovieWithId(movieId)).thenReturn(dummyDetail)

        val movieDetailEntity = LiveDataTestUtil.getValue(filmRepository.getMovieWithId(movieId))
        verify(local).getMovieWithId(movieId)
        assertNotNull(movieDetailEntity)
        assertEquals(movieDetail.id, movieDetailEntity.data?.id)
    }

    @Test
    fun getFavMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavMovie()).thenReturn(dataSourceFactory)
        filmRepository.getFavMovie()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DummyFilm.getMovies()))
        verify(local).getFavMovie()
        assertNotNull(movieEntities)
        assertEquals(moviesResponse.size, movieEntities.data?.size)
    }

    @Test
    fun setFavMovie() {
        filmRepository.setFavMovie(DummyFilm.getDetailMovies(), true)
        verify(local).setFavoriteMovie(DummyFilm.getDetailMovies(), true)
        verifyNoMoreInteractions(local)
    }

    @Test
    fun getTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShows()).thenReturn(dataSourceFactory)
        filmRepository.getAllTvShows()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DummyFilm.getTvShows()))
        verify(local).getAllTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponse.size, tvShowEntities.data?.size)
    }

    @Test
    fun getDetailTvShow() {
        val dummyDetail = MutableLiveData<TvShowEntity>()
        dummyDetail.value = DummyFilm.getDetailTvShows()
        `when`(local.getTvShowWithId(tvShowId)).thenReturn(dummyDetail)

        val tvShowDetailEntity = LiveDataTestUtil.getValue(filmRepository.getTvShowWithId(tvShowId))
        verify(local).getTvShowWithId(tvShowId)
        assertNotNull(tvShowDetailEntity)
        assertEquals(tvShowDetail.id, tvShowDetailEntity.data?.id)
    }

    @Test
    fun getFavTvShow() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getFavTvShow()).thenReturn(dataSourceFactory)
        filmRepository.getFavTvShow()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DummyFilm.getTvShows()))
        verify(local).getFavTvShow()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponse.size, tvShowEntities.data?.size)
    }

    @Test
    fun setFavTvShow() {
        filmRepository.setFavTvShow(DummyFilm.getDetailTvShows(), true)
        verify(local).setFavoriteTvShow(DummyFilm.getDetailTvShows(), true)
        verifyNoMoreInteractions(local)
    }

}