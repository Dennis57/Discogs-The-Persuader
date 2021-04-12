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
//import com.example.thepersuader.network.DiscogsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReleaseDetailViewModel @Inject constructor(private val discogsApplication: DiscogsApplication, private val retrofitService: DiscogsApiService) : ViewModel() {

    private var _releaseDetails = MutableLiveData<ReleaseDetailUiModel>()

    val releaseDetails: LiveData<ReleaseDetailUiModel>
        get() = _releaseDetails

    private var _showLoading = MutableLiveData<Boolean>()

    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private var _releaseDetailError = MutableLiveData<Boolean>()

    val releaseDetailError: LiveData<Boolean>
        get() = _releaseDetailError

    @SuppressLint("CheckResult")
    fun getReleaseDetails(id: Int?) {
        try {
            retrofitService.getReleaseDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _showLoading.value = true }
                .doOnTerminate { _showLoading.value = false }
                .subscribe({
                    if (it.isSuccessful) {
                        it.body()?.let { releaseDetailResponse ->

                            val trackUiModels = releaseDetailResponse.trackList?.map { trackResponse ->
                                TrackListUiModel(
                                    trackResponse.title.orEmpty(),
                                    trackResponse.duration ?: "-"
                                )
                            }

                            val videoUiModels = mutableListOf<VideosUiModel>()
                            releaseDetailResponse.videos?.forEach { videoResponse ->
                                videoUiModels.add(
                                    VideosUiModel(
                                        videoResponse.title.orEmpty(),
                                        videoResponse.uri.orEmpty()
                                    )
                                )
                            }

//                            val artists2: List<String?> = releaseDetailResponse.artists?.map { artist -> artist.name }.orEmpty()
                            val artists: String =
                                releaseDetailResponse.artists?.map { artist -> artist.name }
                                    ?.joinToString(", ") ?: "Unspecificed"

                            val releaseDetailUiModel = ReleaseDetailUiModel(
                                releaseDetailResponse.id ?: 0,
                                releaseDetailResponse.title.orEmpty(),
                                releaseDetailResponse.year ?: 0,
                                artists,
                                trackUiModels.orEmpty(),
                                videoUiModels
                            )
                            _releaseDetails.value = releaseDetailUiModel
                        }
                    }
                }, {
                    Log.e("<Error>", "Error: " + it.message)
                    _releaseDetailError.value = true
                })
        } catch (e: Exception) {
            Log.e("<Error>", "Error: " + e.message)
            _releaseDetailError.value = true
        }
    }
}