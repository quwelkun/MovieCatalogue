package com.example.moviecatalogue.ui.movies.favoritemovie

import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.FilmRepository
import com.example.moviecatalogue.data.source.local.entity.MovieEntity

class MovieFavoriteViewModel(private val filmRepository: FilmRepository): ViewModel() {
    fun getFavMovie() = filmRepository.getFavMovie()

    fun setFavMovie(movieEntity: MovieEntity) {
        val newState = !movieEntity.fav
        filmRepository.setFavMovie(movieEntity, newState)
    }
}