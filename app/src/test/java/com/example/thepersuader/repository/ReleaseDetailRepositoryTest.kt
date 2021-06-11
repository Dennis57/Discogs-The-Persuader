package com.example.thepersuader.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.thepersuader.model.releaseDetail.*
import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.room.DiscogsDatabaseDao
import com.example.thepersuader.room.ReleaseDetailEntity
import com.example.thepersuader.room.TrackListEntity
import com.example.thepersuader.room.VideoEntity
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import java.net.ConnectException
import org.mockito.Mockito.`when` as whenever

class ReleaseDetailRepositoryTest {

  @get:Rule
  var taskExecutorRule = InstantTaskExecutorRule()

  private lateinit var releaseDetailRepository: ReleaseDetailRepository

  private lateinit var discogsDatabaseDao: DiscogsDatabaseDao

  private lateinit var retrofitService: DiscogsApiService

  @Before
  fun setup() {
    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    discogsDatabaseDao = Mockito.mock(DiscogsDatabaseDao::class.java)
    retrofitService = Mockito.mock(DiscogsApiService::class.java)
    releaseDetailRepository = ReleaseDetailRepository(discogsDatabaseDao, retrofitService)
  }

  @Test
  fun getReleaseDetail_Valid_Success() {
    val artistResponse: List<ArtistResponse> = listOf(ArtistResponse("Dennis DC"))
    val trackListUiModelList: List<TrackListUiModel> =
      listOf(TrackListUiModel("Lagu Dennis", "1:02"))
    val trackListResponseList: List<TrackResponse> = listOf(TrackResponse("Lagu Dennis", "1:02"))
    val videosUiModelList: List<VideosUiModel> =
      listOf(VideosUiModel("Video Dennis", "www.google.com"))
    val videosResponseList: List<VideoResponse> =
      listOf(VideoResponse("Video Dennis", "www.google.com"))
    whenever(retrofitService.getReleaseDetail(Mockito.anyInt())).thenReturn(
      Observable.just(
        Response.success(
          ReleaseDetailResponse(
            1, "Dennis", 2000, artistResponse, trackListResponseList, videosResponseList
          )
        )
      )
    )
    releaseDetailRepository.getReleaseDetail(1).subscribe {
      assertEquals(
        it, ReleaseDetailUiModel(
          1, "Dennis", 2000, "Dennis DC", trackListUiModelList, videosUiModelList
        )
      )
    }
  }

  @Test
  fun getReleaseDetail_Null_Success() {
    val trackListUiModelList: List<TrackListUiModel> =
      listOf(TrackListUiModel("Lagu Dennis", "1:02"))
    val trackListEntityList: List<TrackListEntity> =
      listOf(TrackListEntity(1, 1, "Lagu Dennis", "1:02"))
    val videosUiModelList: List<VideosUiModel> =
      listOf(VideosUiModel("Video Dennis", "www.google.com"))
    val videosEntityList: List<VideoEntity> =
      listOf(VideoEntity(1, 1, "Video Dennis", "www.google.com"))
    whenever(retrofitService.getReleaseDetail(Mockito.anyInt())).thenReturn(
      Observable.error(
        ConnectException()
      )
    )
    whenever(discogsDatabaseDao.getReleaseDetail(Mockito.anyInt())).thenReturn(
      Observable.just(
        ReleaseDetailEntity(1, "Dennis", 2000, "Dennis DC")
      )
    )
    whenever(discogsDatabaseDao.getTrackList(Mockito.anyInt())).thenReturn(trackListEntityList)
    whenever(discogsDatabaseDao.getVideos(Mockito.anyInt())).thenReturn(videosEntityList)
    releaseDetailRepository.getReleaseDetail(1).subscribe {
      assertEquals(
        it, ReleaseDetailUiModel(
          1, "Dennis", 2000, "Dennis DC", trackListUiModelList, videosUiModelList
        )
      )
    }
  }
}