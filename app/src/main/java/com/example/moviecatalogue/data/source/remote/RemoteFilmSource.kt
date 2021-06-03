package com.example.moviecatalogue.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviecatalogue.data.source.remote.response.DetailResponse
import com.example.moviecatalogue.data.source.remote.response.MovieResponse
import com.example.moviecatalogue.data.source.remote.response.TvShowResponse
import com.example.moviecatalogue.utils.EspressoIdlingResource
import com.example.moviecatalogue.utils.JsonHelper

class RemoteFilmSource private constructor(private val jsonHelper: JsonHelper) {

    private val handler = Handler(Looper.getMainLooper())

    companion object {

        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteFilmSource? = null

        fun getInstance(helper: JsonHelper): RemoteFilmSource =
            instance ?: synchronized(this) {
                instance ?: RemoteFilmSource(helper).apply { instance = this }
            }
    }

    fun getAllMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        handler.postDelayed(
            {
                resultMovie.value = ApiResponse.success(jsonHelper.loadAllMovies("movies.json"))
                EspressoIdlingResource.decrement()
            }, SERVICE_LATENCY_IN_MILLIS)

        return resultMovie
    }

    fun getAllTvShows(): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        handler.postDelayed(
            {
                resultMovie.value = ApiResponse.success(jsonHelper.loadAllTvShows("tvshows.json"))
                EspressoIdlingResource.decrement()
            }, SERVICE_LATENCY_IN_MILLIS
        )

        return resultMovie
    }

    fun getMovieWithId(movieId: String): LiveData<ApiResponse<DetailResponse>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<DetailResponse>>()
        handler.postDelayed(
            { result.value = ApiResponse.success(jsonHelper.loadDetailMovie(movieId))
                EspressoIdlingResource.decrement()},
            SERVICE_LATENCY_IN_MILLIS
        )
        return result
    }

    fun getTvShowWithId(tvshowId: String): LiveData<ApiResponse<DetailResponse>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<DetailResponse>>()
        handler.postDelayed(
            {
                result.value = ApiResponse.success(jsonHelper.loadDetailTvShow(tvshowId))
            EspressoIdlingResource.decrement()},
            SERVICE_LATENCY_IN_MILLIS
        )
        return result
    }

    interface LoadMoviesCallback {
        fun onAllMoviesReceived(filmResponses: List<MovieResponse>)
    }

    interface LoadTvShowsCallback {
        fun onAllTvShowsReceived(filmResponses: List<TvShowResponse>)
    }

    interface LoadMovieWithIdCallback {
        fun onMovieRecieved(detailResponse: DetailResponse)
    }

    interface LoadTvShowWithIdCallback {
        fun onTvShowRecieved(detailResponse: DetailResponse)
    }
}