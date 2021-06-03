package com.example.moviecatalogue.ui.movies.homemovie

import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.FilmRepository

class MoviesViewModel(private val filmRepository: FilmRepository): ViewModel() {
    fun getMovies() = filmRepository.getAllMovies()
}