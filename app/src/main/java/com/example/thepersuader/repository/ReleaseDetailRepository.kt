package com.example.thepersuader.repository

import com.example.thepersuader.model.releaseDetail.ReleaseDetailUiModel
import com.example.thepersuader.model.releaseDetail.TrackListUiModel
import com.example.thepersuader.model.releaseDetail.VideosUiModel
import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.room.DiscogsDatabaseDao
import com.example.thepersuader.room.ReleaseDetailEntity
import com.example.thepersuader.room.TrackListEntity
import com.example.thepersuader.room.VideoEntity
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class ReleaseDetailRepository @Inject constructor(
  private val discogsDatabaseDao: DiscogsDatabaseDao, private val retrofitService: DiscogsApiService
) {

  fun getReleaseDetail(id: Int): Observable<ReleaseDetailUiModel> {
    return retrofitService.getReleaseDetail(id).onErrorReturnItem(
      Response.success(null)
    )
      .flatMap {
      it.body()?.let { releaseDetailResponse ->

        val trackUiModels = releaseDetailResponse.trackList?.map { trackResponse ->
          TrackListUiModel(
            trackResponse.title.orEmpty(), trackResponse.duration ?: "-"
          )
        }

        val trackListEntity = releaseDetailResponse.trackList?.map { trackResponse ->
          TrackListEntity(
            releaseDetailId = id, title = trackResponse.title.orEmpty(), duration = trackResponse.duration.orEmpty()
          )
        }

        val videoUiModels = releaseDetailResponse.videos?.map { videoResponse ->
          VideosUiModel(
            videoResponse.title.orEmpty(), videoResponse.uri.orEmpty()
          )
        }

        val videoEntity = releaseDetailResponse.videos?.map { videoResponse ->
          VideoEntity(
            releaseDetailId = id, title = videoResponse.title.orEmpty(), uri = videoResponse.uri.orEmpty()
          )
        }

        val artists: String =
          releaseDetailResponse.artists?.map { artist -> artist.name }?.joinToString(", ")
            ?: "Unspecified"

        val releaseDetailEntity = ReleaseDetailEntity(
          releaseDetailResponse.id ?: 0,
          releaseDetailResponse.title.orEmpty(),
          releaseDetailResponse.year ?: 0,
          artists
        )

        val releaseDetailUiModel = ReleaseDetailUiModel(
          releaseDetailResponse.id ?: 0,
          releaseDetailResponse.title.orEmpty(),
          releaseDetailResponse.year ?: 0,
          artists,
          trackUiModels.orEmpty(),
          videoUiModels.orEmpty()
        )

        discogsDatabaseDao.insertReleaseDetail(releaseDetailEntity)
        discogsDatabaseDao.insertTrackList(trackListEntity.orEmpty())
        discogsDatabaseDao.insertVideos(videoEntity.orEmpty())

        Observable.just(releaseDetailUiModel)
      }?: kotlin.run {
        discogsDatabaseDao.getReleaseDetail(id).map { releaseDetailEntity ->
          ReleaseDetailUiModel(
            releaseDetailEntity.id,
            releaseDetailEntity.title,
            releaseDetailEntity.year,
            releaseDetailEntity.artists,
            getTrackList(id),
            getVideos(id)
          )
        }
      }
    }
  }

  private fun getTrackList(id: Int): List<TrackListUiModel> {
    return discogsDatabaseDao.getTrackList(id).map {
      TrackListUiModel(it.title, it.duration)
    }
  }

  private fun getVideos(id: Int): List<VideosUiModel> {
    return discogsDatabaseDao.getVideos(id).map {
      VideosUiModel(it.title, it.uri)
    }
  }
}