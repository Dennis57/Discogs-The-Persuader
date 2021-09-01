package com.example.thepersuader.releaseDetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thepersuader.DiscogsApplication
import com.example.thepersuader.model.releaseDetail.ReleaseDetailUiModel
import com.example.thepersuader.model.releaseDetail.TrackListUiModel
import com.example.thepersuader.model.releaseDetail.VideosUiModel
import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.repository.ReleaseDetailRepository //import com.example.thepersuader.network.DiscogsApi
import com.example.thepersuader.repository.ReleaseDetailRepositoryInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReleaseDetailViewModel @Inject constructor(private val releaseDetailRepository: ReleaseDetailRepositoryInterface) :
  ViewModel() {

  private var _releaseDetails = MutableLiveData<ReleaseDetailUiModel>()

  val releaseDetails: LiveData<ReleaseDetailUiModel>
    get() = _releaseDetails

  private var _showLoading = MutableLiveData<Boolean>()

  val showLoading: LiveData<Boolean>
    get() = _showLoading

  private var _releaseDetailError = MutableLiveData<Boolean>()

  val releaseDetailError: LiveData<Boolean>
    get() = _releaseDetailError

  @SuppressLint("CheckResult") fun getReleaseDetails(id: Int) {
    releaseDetailRepository.getReleaseDetail(id).subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe { _showLoading.value = true }
      .doOnTerminate { _showLoading.value = false }.subscribe({
        if (it != null) {
          _releaseDetails.value = it
        } else {
          _releaseDetailError.value = true
        }
        _showLoading.value = false
      }, {
        _releaseDetailError.value = true
        _showLoading.value = false
      })
  }
}