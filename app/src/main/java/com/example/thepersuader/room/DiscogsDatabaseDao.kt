package com.example.thepersuader.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface DiscogsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtist(artist: ArtistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRelease(release: List<ReleaseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReleaseDetail(releaseDetail : ReleaseDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrackList(trackList: List<TrackListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideos(videos: List<VideoEntity>)

    @Query("SELECT * FROM artist_table WHERE artist_id = :artistId")
    fun getArtist(artistId: Int): Observable<ArtistEntity>

    @Query("SELECT * FROM release_table WHERE artist_id = :artistId ORDER BY year DESC")
    fun getReleases(artistId: Int): Observable<List<ReleaseEntity>>

    @Query("SELECT * FROM release_detail_table WHERE release_detail_id = :releaseDetailId")
    fun getReleaseDetail(releaseDetailId: Int): Observable<ReleaseDetailEntity>

    @Query("SELECT * FROM track_list_table WHERE release_detail_id = :releaseDetailId")
    fun getTrackList(releaseDetailId: Int): List<TrackListEntity>

    @Query("SELECT * FROM video_table WHERE release_detail_id = :releaseDetailId")
    fun getVideos(releaseDetailId: Int): List<VideoEntity>

}