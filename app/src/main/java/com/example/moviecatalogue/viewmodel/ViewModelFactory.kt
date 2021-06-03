package com.example.moviecatalogue.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalogue.data.FilmRepository
import com.example.moviecatalogue.di.Injection
import com.example.moviecatalogue.ui.detail.DetailFilmViewModel
import com.example.moviecatalogue.ui.movies.homemovie.MoviesViewModel
import com.example.moviecatalogue.ui.movies.favoritemovie.MovieFavoriteViewModel
import com.example.moviecatalogue.ui.tvshow.hometvshow.TvShowViewModel
import com.example.moviecatalogue.ui.tvshow.favoritetvshow.TvShowFavoriteViewModel

class ViewModelFactory private constructor(private val mFilmRepository: FilmRepository)
    :ViewModelProvider.NewInstanceFactory(){

        companion object{
            @Volatile
            private  var instance: ViewModelFactory? = null

            fun getInstance(context: Context): ViewModelFactory =
                    instance ?: synchronized(this ){
                        instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                            instance = this
                        }
                    }
        }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                return MoviesViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                return TvShowViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(DetailFilmViewModel::class.java) -> {
                return DetailFilmViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(MovieFavoriteViewModel::class.java) -> {
                return MovieFavoriteViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(TvShowFavoriteViewModel::class.java) -> {
                return TvShowFavoriteViewModel(mFilmRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class:" + modelClass.name)
        }
    }

}