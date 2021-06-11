package com.example.thepersuader.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "release_detail_table")
data class ReleaseDetailEntity(

    @PrimaryKey
    @ColumnInfo(name = "release_detail_id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "year")
    val year: Int,

    @ColumnInfo(name = "artists")
    val artists: String
)

@Entity(tableName = "track_list_table")
data class TrackListEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "track_id")
    val trackId: Int = 0,

    @ColumnInfo(name = "release_detail_id")
    val releaseDetailId: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "duration")
    val duration: String
)

@Entity(tableName = "video_table")
data class VideoEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "video_id")
    val videoId: Int = 0,

    @ColumnInfo(name = "release_detail_id")
    val releaseDetailId: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "uri")
    val uri: String
)