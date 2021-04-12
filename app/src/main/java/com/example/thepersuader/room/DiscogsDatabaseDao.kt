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

    @Insert
    fun insertReleaseDetail(releaseDetail : ReleaseDetailEntity)

    @Query("SELECT * FROM artist_table WHERE artist_id = :artistId")
    fun getArtist(artistId: Int): Observable<ArtistEntity>

    @Query("SELECT * FROM release_table WHERE artist_id = :artistId")
    fun getReleases(artistId: Int): Observable<List<ReleaseEntity>>

    @Query("SELECT * FROM release_detail_table WHERE release_detail_id = :releaseDetailId")
    fun getReleaseDetail(releaseDetailId: Int): Observable<ReleaseDetailEntity>

    @Query("SELECT * FROM track_list_table WHERE release_detail_id = :releaseDetailId")
    fun getTrackList(releaseDetailId: Int): Observable<List<TrackListEntity>>

    @Query("SELECT * FROM video_table WHERE release_detail_id = :releaseDetailId")
    fun getVideos(releaseDetailId: Int): Observable<List<VideoEntity>>

}