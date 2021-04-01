package com.example.thepersuader.ReleaseDetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thepersuader.Model.ReleaseDetail.ReleaseDetailUiModel
import com.example.thepersuader.Model.ReleaseDetail.TrackListUiModel
import com.example.thepersuader.Model.ReleaseDetail.VideosUiModel
import com.example.thepersuader.Network.DiscogsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ReleaseDetailViewModel : ViewModel(){

    private var _releaseDetails = MutableLiveData<ReleaseDetailUiModel>()

    val releaseDetails : LiveData<ReleaseDetailUiModel>
        get() = _releaseDetails

    @SuppressLint("CheckResult")
    fun getReleaseDetails(id: Int?) {
        try {
            DiscogsApi.retrofitService.getReleaseDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful) {
                        it.body()?.let { releaseDetailResponse ->

                            val trackUiModels = mutableListOf<TrackListUiModel>()
                            releaseDetailResponse.trackList?.forEach { trackResponse ->
                                trackUiModels.add(TrackListUiModel(trackResponse.title.orEmpty(), trackResponse.duration.orEmpty()))
                            }

                            val videoUiModels = mutableListOf<VideosUiModel>()
                            releaseDetailResponse.videos?.forEach { videoResponse ->
                                videoUiModels.add(VideosUiModel(videoResponse.title.orEmpty(), videoResponse.uri.orEmpty()))
                            }
//                            val artists2: List<String?> = releaseDetailResponse.artists?.map { artist -> artist.name }.orEmpty()
                            val artists: String = releaseDetailResponse.artists?.map { artist -> artist.name }?.joinToString ( ", " )?:"Unspecificed"

                            val releaseDetailUiModel = ReleaseDetailUiModel(releaseDetailResponse.id ?: 0, releaseDetailResponse.title.orEmpty(), releaseDetailResponse.year ?: 0, artists, trackUiModels, videoUiModels)
                            _releaseDetails.value = releaseDetailUiModel
                        }
                    }
                },{
                    Log.e("<Error>", "Error: " + it.message)
                })
        } catch (e: Exception) {
            Log.e("<Error>", "Error: " + e.message)
        }
    }
}