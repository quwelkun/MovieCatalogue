package com.example.moviecatalogue.ui.detail

import android.graphics.Movie
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.moviecatalogue.data.source.local.entity.DetailEntity
import com.example.moviecatalogue.data.FilmRepository
import com.example.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.moviecatalogue.utils.DummyFilm
import com.example.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailFilmViewModelTest {

    private lateinit var viewModel: DetailFilmViewModel
    private val dummyMovie = DummyFilm.getDetailMovies()
    private val dummyTvShow = DummyFilm.getDetailTvShows()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observerMovie: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var observerTvShow: Observer<Resource<TvShowEntity>>


    @Mock
    private lateinit var filmRepository: FilmRepository

    @Before
    fun setUp() {
        viewModel = DetailFilmViewModel(filmRepository)
    }

    @Test
    fun getFilmMovies() {

        val dummyDetailMovie = Resource.success(DummyFilm.getDetailMovies())
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        `when`(filmRepository.getMovieWithId(dummyMovie.id)).thenReturn(movie)
        viewModel.setFilm(dummyMovie.id, "movie")
        viewModel.getMovieDetail().observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyDetailMovie)

    }

    @Test
    fun getFilmTvShow() {
        val dummyDetailMovie = Resource.success(DummyFilm.getDetailTvShows())
        val tvshow = MutableLiveData<Resource<TvShowEntity>>()
        tvshow.value = dummyDetailMovie

        `when`(filmRepository.getTvShowWithId(dummyTvShow.id)).thenReturn(tvshow)
        viewModel.setFilm(dummyTvShow.id, "tv_show")
        viewModel.getTvShowDetail().observeForever(observerTvShow)
        verify(observerTvShow).onChanged(dummyDetailMovie)
    }

    @Test
    fun setFavoriteMovie() {
        val dummyDetailMovie = Resource.success(DummyFilm.getDetailMovies())
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        `when`(filmRepository.getMovieWithId(dummyMovie.id)).thenReturn(movie)

        viewModel.setFilm(dummyMovie.id.toString(), "movie")
        viewModel.setFavoriteMovie()
        verify(filmRepository).setFavMovie(movie.value!!.data as MovieEntity, true)
        verifyNoMoreInteractions(observerMovie)
    }

    @Test
    fun setFavoriteTvShow() {
        val dummyDetailTvShow = Resource.success(DummyFilm.getDetailTvShows())
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = dummyDetailTvShow

        `when`(filmRepository.getTvShowWithId(dummyTvShow.id)).thenReturn(tvShow)

        viewModel.setFilm(dummyTvShow.id.toString(), "tv_show")
        viewModel.setFavoriteTvShow()
        verify(filmRepository).setFavTvShow(tvShow.value!!.data as TvShowEntity, true)
        verifyNoMoreInteractions(observerTvShow)
    }
}