package com.example.moviecatalogue.di

import android.content.Context
import com.example.moviecatalogue.data.FilmRepository
import com.example.moviecatalogue.data.source.local.LocalDataSource
import com.example.moviecatalogue.data.source.local.room.FilmDatabase
import com.example.moviecatalogue.data.source.remote.RemoteFilmSource
import com.example.moviecatalogue.utils.AppExecutors
import com.example.moviecatalogue.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): FilmRepository {

        val database = FilmDatabase.getInstance(context)


        val remoteFilmSource = RemoteFilmSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.filmDao())
        val appExecutors = AppExecutors()
        return FilmRepository.getInstance(remoteFilmSource, localDataSource, appExecutors)
    }
}