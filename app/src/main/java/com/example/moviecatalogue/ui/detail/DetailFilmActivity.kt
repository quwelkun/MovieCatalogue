package com.example.moviecatalogue.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.source.local.entity.MovieEntity
import com.example.moviecatalogue.data.source.local.entity.TvShowEntity
import com.example.moviecatalogue.databinding.ActivityDetailFilmBinding
import com.example.moviecatalogue.viewmodel.ViewModelFactory
import com.example.moviecatalogue.vo.Status

class DetailFilmActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
    }

    private lateinit var activityDetailFilmBinding: ActivityDetailFilmBinding
    private lateinit var viewModel: DetailFilmViewModel

    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailFilmBinding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(activityDetailFilmBinding.root)

        supportActionBar?.hide()

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailFilmViewModel::class.java]


        val extras = intent.extras
        if (extras != null) {
            val id = extras.getString(EXTRA_ID)
            type = extras.getString(EXTRA_TYPE)

            if (id != null && type != null) {
                progressBarState(state = false)
                viewModel.setFilm(id, type.toString())

                favoriteState()
                typeState()
            }
        }

        activityDetailFilmBinding.fabAddToFav.setOnClickListener {
            when(type) {
                "movie" -> {
                    viewModel.setFavoriteMovie()
                }
                "tv_show" -> {
                    viewModel.setFavoriteTvShow()
                }
            }

        }
    }

    private fun populateDetailMovie(movie: MovieEntity) {
        progressBarState(state = true)

        activityDetailFilmBinding.tvDetailGenre.text = movie.genre
        activityDetailFilmBinding.tvDetailDuration.text = movie.duration
        activityDetailFilmBinding.collapsing.title = movie.title
        activityDetailFilmBinding.tvDetailOverview.text = movie.overview
        Glide.with(this)
            .load(movie.poster)
            .into(activityDetailFilmBinding.ivDetail)
    }

    private fun populateDetailTvShow(movie: TvShowEntity) {
        progressBarState(state = true)

        activityDetailFilmBinding.tvDetailGenre.text = movie.genre
        activityDetailFilmBinding.tvDetailDuration.text = movie.duration
        activityDetailFilmBinding.collapsing.title = movie.title
        activityDetailFilmBinding.tvDetailOverview.text = movie.overview
        Glide.with(this)
            .load(movie.poster)
            .into(activityDetailFilmBinding.ivDetail)
    }

    private fun progressBarState(state: Boolean) {
        activityDetailFilmBinding.progressBar.isVisible = !state
        activityDetailFilmBinding.appbar.isVisible = state
        activityDetailFilmBinding.nestedScrolView.isVisible = state
        activityDetailFilmBinding.fabAddToFav.isVisible = state
    }

    private fun favoriteState() {
        if (type == "movie") {
            viewModel.getMovieDetail().observe(this, { movie ->
                when (movie.status) {
                    Status.LOADING -> progressBarState(false)
                    Status.SUCCESS -> {
                        if (movie.data != null) {
                            progressBarState(true)
                            val state = movie.data.fav
                            setFavorite(state)
                        }
                    }
                    Status.ERROR -> {
                        progressBarState(false)
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else if (type == "tv_show") {
            viewModel.getTvShowDetail().observe(this, { tvShow ->
                when (tvShow.status) {
                    Status.LOADING -> progressBarState(false)
                    Status.SUCCESS -> {
                        if (tvShow.data != null) {
                            progressBarState(true)
                            val state = tvShow.data.fav
                            setFavorite(state)
                        }
                    }
                    Status.ERROR -> {
                        progressBarState(false)
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun typeState() {
        if(type == "movie") {
            viewModel.getMovieDetail().observe(this, {movieDetail ->
                when(movieDetail.status) {
                    Status.LOADING -> progressBarState(state = false)
                    Status.SUCCESS -> {
                        if(movieDetail.data != null) {
                            progressBarState(true)
                            populateDetailMovie(movieDetail.data)
                        }
                    }
                    Status.ERROR -> {
                        progressBarState(false)
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else if(type == "tv_show") {
            viewModel.getTvShowDetail().observe(this, {tvshowDetail ->
                when(tvshowDetail.status) {
                    Status.LOADING -> progressBarState(state = false)
                    Status.SUCCESS -> {
                        if(tvshowDetail.data != null) {
                            progressBarState(true)
                            populateDetailTvShow(tvshowDetail.data)
                        }
                    }
                    Status.ERROR -> {
                        progressBarState(false)
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun setFavorite(state: Boolean) {
        val fab = activityDetailFilmBinding.fabAddToFav
        if(state) {
            fab.setImageResource(R.drawable.ic_fav)
        } else {
            fab.setImageResource(R.drawable.ic_unfav)
        }
    }

}