package com.example.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.FilmRepository
import com.example.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.moviecatalogue.vo.Resource

class DetailFilmViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    private lateinit var tvshowDetail: LiveData<Resource<TvShowEntity>>
    private lateinit var movieDetail: LiveData<Resource<MovieEntity>>

    fun setFilm(id: String, type: String) {
        when(type) {
            "movie" -> {
                movieDetail = filmRepository.getMovieWithId(id)
            }

            "tv_show" -> {
                tvshowDetail = filmRepository.getTvShowWithId(id)
            }
        }
    }

    fun setFavoriteMovie() {
        val resource = movieDetail.value
        if (resource?.data != null) {
            val newState = !resource.data.fav
            filmRepository.setFavMovie(resource.data, newState)
        }
    }

    fun setFavoriteTvShow() {
        val resource = tvshowDetail.value
        if (resource?.data != null) {
            val newState = !resource.data.fav
            filmRepository.setFavTvShow(resource.data, newState)
        }
    }

    fun getMovieDetail() = movieDetail
    fun getTvShowDetail() = tvshowDetail

}