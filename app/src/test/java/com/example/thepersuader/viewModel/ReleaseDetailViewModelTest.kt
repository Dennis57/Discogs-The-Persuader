package com.example.thepersuader.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.thepersuader.model.releaseDetail.ReleaseDetailUiModel
import com.example.thepersuader.model.releaseDetail.TrackListUiModel
import com.example.thepersuader.model.releaseDetail.VideosUiModel
import com.example.thepersuader.releaseDetail.ReleaseDetailViewModel
import com.example.thepersuader.repository.ReleaseDetailRepository
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when` as whenever

class ReleaseDetailViewModelTest {

  @get:Rule
  var taskExecutorRule = InstantTaskExecutorRule()

  private lateinit var releaseDetailRepository: ReleaseDetailRepository

  private lateinit var viewModel: ReleaseDetailViewModel

  @Before fun setup() {
    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    releaseDetailRepository = Mockito.mock(ReleaseDetailRepository::class.java)
    viewModel = ReleaseDetailViewModel(releaseDetailRepository)
  }

  @Test fun getReleaseDetail_Valid_Success() {
    val trackListUiModelList: List<TrackListUiModel> =
      listOf(TrackListUiModel("Lagu Dennis", "1:02"))
    val videosUiModelList: List<VideosUiModel> =
      listOf(VideosUiModel("Video Dennis", "www.google.com"))
    whenever(releaseDetailRepository.getReleaseDetail(Mockito.anyInt())).thenReturn(
      Observable.just(
        ReleaseDetailUiModel(
          1,
          "Dennis",
          2000,
          "Dennis DC",
          trackListUiModelList,
          videosUiModelList
        )
      )
    )
    viewModel.getReleaseDetails(1)
    viewModel.releaseDetails.observeForever {
      assertEquals(
        it,
        ReleaseDetailUiModel(
          1,
          "Dennis",
          2000,
          "Dennis DC",
          trackListUiModelList,
          videosUiModelList
        )
      )
    }

  }

}