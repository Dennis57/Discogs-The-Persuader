package com.example.thepersuader.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thepersuader.DiscogsApplication
import com.example.thepersuader.model.artist.ArtistUiModel
import com.example.thepersuader.model.release.ReleaseUiModel
import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.repository.ArtistRepository
import com.example.thepersuader.repository.ReleaseRepository
import com.example.thepersuader.room.DiscogsDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val artistRepository: ArtistRepository, private val releaseRepository: ReleaseRepository
) : ViewModel() {

  private var _releases = MutableLiveData<List<ReleaseUiModel>>()

  val releases: LiveData<List<ReleaseUiModel>>
    get() = _releases

  private var _artist = MutableLiveData<ArtistUiModel>()

  val artist: LiveData<ArtistUiModel>
    get() = _artist

  private var _showLoading = MutableLiveData<Boolean>()

  val showLoading: LiveData<Boolean>
    get() = _showLoading

  private var _artistError = MutableLiveData<Boolean>()

  val artistError: LiveData<Boolean>
    get() = _artistError

  @SuppressLint("CheckResult") fun getArtist() {
    artistRepository.getArtist(1).subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
        _showLoading.value = true
      }.doOnTerminate { _showLoading.value = false }.subscribe({
        if (it != null) {
          _artist.value = it
        } else {
          _artistError.value = true
        }
        _showLoading.value = false
      }, {
        _artistError.value = true
        _showLoading.value = false
      })
  }

  @SuppressLint("CheckResult") fun getReleases() {
    releaseRepository.getReleases(1).subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread()).subscribe {
        _releases.value = it
      }
  }
}