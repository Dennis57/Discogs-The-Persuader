package com.example.thepersuader.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "release_table")
data class ReleaseEntity(

    @PrimaryKey
    @ColumnInfo(name = "release_id")
    val id: Int,

    @ColumnInfo(name = "artist_id")
    val artist_id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "year")
    val year: Int
)


