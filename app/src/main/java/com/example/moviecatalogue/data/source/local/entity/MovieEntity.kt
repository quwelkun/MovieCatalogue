package com.example.moviecatalogue.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entities")
data class MovieEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "id")
        var id: String,

        @ColumnInfo(name = "title")
        var title: String,

        @ColumnInfo(name = "overview")
        var overview: String,

        @ColumnInfo(name = "duration")
        var duration: String,

        @ColumnInfo(name = "genre")
        var genre: String,

        @ColumnInfo(name = "poster")
        var poster: String,

        @ColumnInfo(name = "type")
        var type: String,

        @ColumnInfo(name = "fav")
        var fav: Boolean = FavStatus.NOT_FAV
)