package com.example.thepersuader.repository

import com.example.thepersuader.model.releaseDetail.ReleaseDetailUiModel
import io.reactivex.Observable

interface ReleaseDetailRepositoryInterface {
  fun getReleaseDetail(id: Int): Observable<ReleaseDetailUiModel>
}