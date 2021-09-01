package com.example.thepersuader.repository

import com.example.thepersuader.model.artist.ArtistUiModel
import io.reactivex.Observable

interface ArtistRepositoryInterface {
  fun getArtist(artistId: Int): Observable<ArtistUiModel>
}