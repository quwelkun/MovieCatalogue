package com.example.moviecatalogue.utils

import android.content.Context
import com.example.moviecatalogue.data.source.remote.response.DetailResponse
import com.example.moviecatalogue.data.source.remote.response.MovieResponse
import com.example.moviecatalogue.data.source.remote.response.TvShowResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {
    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadAllMovies(fileName: String): List<MovieResponse> {
        val list = ArrayList<MovieResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString(fileName).toString())
            val jsonArray = responseObject.getJSONArray("items")

            for(i in 0 until jsonArray.length()) {
                val film = jsonArray.getJSONObject(i)

                val id = film.getString("id")
                val title = film.getString("title")
                val overview = film.getString("overview")
                val poster = film.getString("poster")
                val genre = film.getString("genres")
                val type = film.getString("type")
                val duration = film.getString("duration")

                val filmResponse = MovieResponse(
                        id = id,
                        title = title,
                        overview = overview,
                        type = type,
                        duration = duration,
                        genre = genre,
                        poster = poster
                )
                list.add(filmResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return list
    }

    fun loadAllTvShows(fileName: String): List<TvShowResponse> {
        val list = ArrayList<TvShowResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString(fileName).toString())
            val jsonArray = responseObject.getJSONArray("items")

            for(i in 0 until jsonArray.length()) {
                val film = jsonArray.getJSONObject(i)

                val id = film.getString("id")
                val title = film.getString("title")
                val overview = film.getString("overview")
                val poster = film.getString("poster")
                val genre = film.getString("genres")
                val type = film.getString("type")
                val duration = film.getString("duration")

                val filmResponse = TvShowResponse(
                    id = id,
                    title = title,
                    overview = overview,
                    type = type,
                    duration = duration,
                    genre = genre,
                    poster = poster
                )
                list.add(filmResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return list
    }

    fun loadDetailMovie(id: String): DetailResponse {
        lateinit var movie: DetailResponse
        try {
            val responseObject = JSONObject(parsingFileToString("movies.json").toString())
            val jsonArray = responseObject.getJSONArray("items")

            for(i in 0 until jsonArray.length()) {
                val film = jsonArray.getJSONObject(i)

                if(film.getString("id") == id) {
                    val movieId = film.getString("id")
                    val title = film.getString("title")
                    val overview = film.getString("overview")
                    val poster = film.getString("poster")
                    val genre = film.getString("genres")
                    val type = film.getString("type")
                    val duration = film.getString("duration")

                    val movieResponse = DetailResponse(
                        id = movieId,
                        title = title,
                        overview = overview,
                        type = type,
                        duration = duration,
                        genre = genre,
                        poster = poster
                    )

                    movie = movieResponse
                    break
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return movie
    }

    fun loadDetailTvShow(id: String): DetailResponse {
        lateinit var tvshow: DetailResponse
        try {
            val responseObject = JSONObject(parsingFileToString("tvshows.json").toString())
            val jsonArray = responseObject.getJSONArray("items")

            for(i in 0 until jsonArray.length()) {
                val film = jsonArray.getJSONObject(i)

                if(film.getString("id") == id) {
                    val movieId = film.getString("id")
                    val title = film.getString("title")
                    val overview = film.getString("overview")
                    val poster = film.getString("poster")
                    val genre = film.getString("genres")
                    val type = film.getString("type")
                    val duration = film.getString("duration")

                    val tvshowResponse = DetailResponse(
                        id = movieId,
                        title = title,
                        overview = overview,
                        type = type,
                        duration = duration,
                        genre = genre,
                        poster = poster
                    )

                    tvshow = tvshowResponse
                    break
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return tvshow
    }
}