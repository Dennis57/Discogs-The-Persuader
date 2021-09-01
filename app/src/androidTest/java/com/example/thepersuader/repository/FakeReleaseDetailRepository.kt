package com.example.thepersuader.repository

import com.example.thepersuader.model.releaseDetail.ReleaseDetailUiModel
import com.example.thepersuader.model.releaseDetail.TrackListUiModel
import com.example.thepersuader.model.releaseDetail.VideosUiModel
import io.reactivex.Observable
import javax.inject.Inject

class FakeReleaseDetailRepository @Inject constructor() : ReleaseDetailRepositoryInterface {

  var trackListUiModels: MutableList<TrackListUiModel> = mutableListOf()
  var videosUiModels: MutableList<VideosUiModel> = mutableListOf()

  override fun getReleaseDetail(id: Int): Observable<ReleaseDetailUiModel> {
    trackListUiModels.add(TrackListUiModel("TrackList 1", "0:10"))
    trackListUiModels.add(TrackListUiModel("TrackList 2", "1:23"))

    videosUiModels.add(VideosUiModel("Video 1", "www.google.com"))
    videosUiModels.add(VideosUiModel("Video 2", "www.google.com"))

    return Observable.just(
      ReleaseDetailUiModel(1, "Album Release 1", 2021, "The Persuader", trackListUiModels, videosUiModels)
    )
  }
}