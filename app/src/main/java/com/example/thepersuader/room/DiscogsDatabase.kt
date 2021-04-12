package com.example.thepersuader.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArtistEntity::class, ReleaseEntity::class, ReleaseDetailEntity::class, TrackListEntity::class, VideoEntity::class], version = 1, exportSchema = false)
abstract class DiscogsDatabase : RoomDatabase() {
    abstract val discogsDatabaseDao : DiscogsDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: DiscogsDatabase? = null

        fun getInstance(context: Context): DiscogsDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DiscogsDatabase::class.java,
                        "discogs_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}