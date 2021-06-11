package com.example.thepersuader.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.thepersuader.model.artist.AliasResponse
import com.example.thepersuader.model.artist.ArtistResponse
import com.example.thepersuader.model.artist.ArtistUiModel
import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.room.ArtistEntity
import com.example.thepersuader.room.DiscogsDatabaseDao
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

class ArtistRepositoryTest {

  @get:Rule
  var taskExecutorRule = InstantTaskExecutorRule()

  private lateinit var artistRepository: ArtistRepository

  private lateinit var discogsDatabaseDao: DiscogsDatabaseDao

  private lateinit var retrofitService: DiscogsApiService

  @Before
  fun setup() {
    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    discogsDatabaseDao = Mockito.mock(DiscogsDatabaseDao::class.java)
    retrofitService = Mockito.mock(DiscogsApiService::class.java)
    artistRepository = ArtistRepository(discogsDatabaseDao, retrofitService)
  }

  @Test
  fun getArtist_Valid_Success() {
    val artistResponse = ArtistResponse(1, "Dennis", "DC", listOf(AliasResponse("Dennis DC")))
    whenever(retrofitService.getArtist()).thenReturn(Observable.just(Response.success(artistResponse)))
    artistRepository.getArtist(1).subscribe { artistUiModel ->
      assertEquals(artistUiModel, ArtistUiModel("Dennis", "DC", "Dennis DC"))
    }
  }

  @Test
  fun getArtist_Null_Success() {
    whenever(retrofitService.getArtist()).thenReturn(Observable.error(UnknownHostException()))
    whenever(discogsDatabaseDao.getArtist(Mockito.anyInt())).thenReturn(
      Observable.just(
        ArtistEntity(
          1,
          "Dennis",
          "DC",
          "Dennis DC"
        )
      )
    )
    artistRepository.getArtist(1).subscribe { artistUiModel ->
      assertEquals(artistUiModel, ArtistUiModel("Dennis", "DC", "Dennis DC"))
    }
  }
}