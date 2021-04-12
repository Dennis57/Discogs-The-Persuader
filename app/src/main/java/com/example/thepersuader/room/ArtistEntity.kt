package com.example.thepersuader.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist_table")
data class ArtistEntity(

    @PrimaryKey
    @ColumnInfo(name = "artist_id")
    val artistId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "real_name")
    val realName: String,

    @ColumnInfo(name = "aliases")
    val aliases: String
)