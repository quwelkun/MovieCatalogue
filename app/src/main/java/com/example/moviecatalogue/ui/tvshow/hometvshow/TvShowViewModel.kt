package com.example.moviecatalogue.ui.tvshow.hometvshow

import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.FilmRepository

class TvShowViewModel(private val filmRepository: FilmRepository): ViewModel() {
    fun getTvShow() = filmRepository.getAllTvShows()
}