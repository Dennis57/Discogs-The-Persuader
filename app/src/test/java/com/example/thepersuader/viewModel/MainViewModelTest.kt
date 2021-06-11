package com.example.thepersuader.viewModel

import com.example.thepersuader.main.MainViewModel
import com.example.thepersuader.model.artist.ArtistUiModel
import com.example.thepersuader.repository.ArtistRepository
import com.example.thepersuader.repository.ReleaseRepository
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
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.thepersuader.model.release.ReleaseUiModel

class MainViewModelTest {

    @get:Rule
    var taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var artistRepository: ArtistRepository

    private lateinit var releaseRepository: ReleaseRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        artistRepository = Mockito.mock(ArtistRepository::class.java)
        releaseRepository = Mockito.mock(ReleaseRepository::class.java)
        viewModel = MainViewModel(artistRepository, releaseRepository)
    }

    @Test
    fun getArtist_Valid_Success() {
        whenever(artistRepository.getArtist(Mockito.anyInt())).thenReturn(Observable.just(ArtistUiModel("Dennis", "DC", "Dennis DC")))
        viewModel.getArtist()
        viewModel.artist.observeForever {
            assertEquals(it, ArtistUiModel("Dennis", "DC", "Dennis DC"))
        }
    }

    @Test
    fun getReleases_Valid_Success() {
        val releaseUiModelList : List<ReleaseUiModel> = listOf(ReleaseUiModel(1, "Dennis' Release", 2000))
        whenever(releaseRepository.getReleases(Mockito.anyInt())).thenReturn(Observable.just(releaseUiModelList))
        viewModel.getReleases()
        viewModel.releases.observeForever {
            assertEquals(it, releaseUiModelList)
        }
    }
}