package com.example.moviecatalogue.ui.tvshow.favoritetvshow

import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.FilmRepository
import com.example.moviecatalogue.data.source.local.entity.TvShowEntity

class TvShowFavoriteViewModel(private val filmRepository: FilmRepository): ViewModel() {
    fun getFavTvShow() = filmRepository.getFavTvShow()

    fun setFavTvShow(tvShowEntity: TvShowEntity) {
        val newState = !tvShowEntity.fav
        filmRepository.setFavTvShow(tvShowEntity, newState)
    }
}