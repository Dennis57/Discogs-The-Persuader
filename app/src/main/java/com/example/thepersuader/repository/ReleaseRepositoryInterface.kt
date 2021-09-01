package com.example.thepersuader.repository

import com.example.thepersuader.model.release.ReleaseUiModel
import io.reactivex.Observable

interface ReleaseRepositoryInterface {
  fun getReleases(artistId: Int): Observable<List<ReleaseUiModel>>
}