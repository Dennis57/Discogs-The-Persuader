package com.example.thepersuader.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.thepersuader.model.release.ReleaseResponse
import com.example.thepersuader.model.release.ReleaseUiModel
import com.example.thepersuader.model.release.Releases
import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.room.DiscogsDatabaseDao
import com.example.thepersuader.room.ReleaseEntity
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
import java.net.UnknownHostException
import org.mockito.Mockito.`when` as whenever

class ReleaseRepositoryTest {

  @get:Rule
  var taskExecutorRule = InstantTaskExecutorRule()

  private lateinit var releaseRepository: ReleaseRepository

  private lateinit var discogsDatabaseDao: DiscogsDatabaseDao

  private lateinit var retrofitService: DiscogsApiService

  @Before
  fun setup() {
    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    discogsDatabaseDao = Mockito.mock(DiscogsDatabaseDao::class.java)
    retrofitService = Mockito.mock(DiscogsApiService::class.java)
    releaseRepository = ReleaseRepository(discogsDatabaseDao, retrofitService)
  }

  @Test
  fun getReleases_Valid_Success() {
    val releaseUiModelList: List<ReleaseUiModel> =
      listOf(ReleaseUiModel(1, "Dennis' Release", 2000))
    val releaseResponseList: ReleaseResponse =
      ReleaseResponse(listOf(Releases(1, "Dennis' Release", 2000)))
    whenever(retrofitService.getReleases(Mockito.anyInt(), Mockito.anyInt())).thenReturn(
      Observable.just(
        Response.success(releaseResponseList)
      )
    )
    releaseRepository.getReleases(1).subscribe {
      assertEquals(it, releaseUiModelList)
    }
  }

  @Test
  fun getReleases_Null_Success() {
    val releaseUiModelList: List<ReleaseUiModel> =
      listOf(ReleaseUiModel(1, "Dennis' Release", 2000))
    val releaseEntityList: List<ReleaseEntity> =
      listOf(ReleaseEntity(1, 1, "Dennis' Release", 2000))
    whenever(
      retrofitService.getReleases(
        Mockito.anyInt(),
        Mockito.anyInt()
      )
    ).thenReturn(Observable.error(UnknownHostException()))
    whenever(discogsDatabaseDao.getReleases(Mockito.anyInt())).thenReturn(
      Observable.just(releaseEntityList)
    )
    releaseRepository.getReleases(1).subscribe {
      assertEquals(it, releaseUiModelList)
    }
  }
}