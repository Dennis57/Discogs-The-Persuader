package com.example.thepersuader.repository

import com.example.thepersuader.model.release.ReleaseUiModel
import io.reactivex.Observable
import javax.inject.Inject

class FakeReleaseRepository @Inject constructor() : ReleaseRepositoryInterface {

  var releaseUiModels: MutableList<ReleaseUiModel> = mutableListOf()

  override fun getReleases(artistId: Int): Observable<List<ReleaseUiModel>> {
    releaseUiModels.add(ReleaseUiModel(1, "Album Release 1", 2021))
    releaseUiModels.add(ReleaseUiModel(2, "Album Release 2", 2020))
    releaseUiModels.add(ReleaseUiModel(3, "Album Release 3", 2019))

    return Observable.just(releaseUiModels)
  }
}