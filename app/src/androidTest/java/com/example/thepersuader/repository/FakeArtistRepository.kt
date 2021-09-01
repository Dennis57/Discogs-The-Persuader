package com.example.thepersuader.repository

import com.example.thepersuader.model.artist.ArtistUiModel
import io.reactivex.Observable
import javax.inject.Inject

class FakeArtistRepository @Inject constructor() : ArtistRepositoryInterface {

  override fun getArtist(artistId: Int): Observable<ArtistUiModel> {
    return Observable.just(
      ArtistUiModel(
        "The Persuader", "Real Persuader", "Persuader Aliases"
      )
    )
  }
}