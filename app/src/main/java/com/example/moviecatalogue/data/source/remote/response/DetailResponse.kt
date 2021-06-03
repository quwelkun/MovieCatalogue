package com.example.moviecatalogue.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailResponse (
    var id: String,
    var title: String,
    var overview: String,
    var genre: String,
    var poster: String,
    var duration: String,
    var type: String,
    var fav: Boolean = false
): Parcelable