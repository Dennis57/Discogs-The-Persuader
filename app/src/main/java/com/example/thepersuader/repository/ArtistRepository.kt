package com.example.thepersuader.repository

import android.util.Log
import com.example.thepersuader.model.artist.AliasResponse
import com.example.thepersuader.model.artist.ArtistResponse
import com.example.thepersuader.model.artist.ArtistUiModel
import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.room.ArtistEntity
import com.example.thepersuader.room.DiscogsDatabaseDao
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class ArtistRepository @Inject constructor(
  private val discogsDatabaseDao: DiscogsDatabaseDao, private val retrofitService: DiscogsApiService
) {

  fun getArtist(artistId: Int): Observable<ArtistUiModel> {

    return retrofitService.getArtist()
      .onErrorReturnItem(
      Response.success(null)
    ).flatMap {
      it.body()?.let { artistResponse ->
        val aliasesList: String =
          artistResponse.aliases?.joinToString(", ") { alias -> alias.name } ?: "Unspecified"

        val artistEntity = ArtistEntity(
          artistResponse.id ?: 0,
          artistResponse.name.orEmpty(),
          artistResponse.real_name.orEmpty(),
          aliasesList
        )

        discogsDatabaseDao.insertArtist(artistEntity)
        Observable.just(
          ArtistUiModel(
            artistEntity.name, artistEntity.realName, artistEntity.aliases
          )
        )
      } ?: kotlin.run {
        discogsDatabaseDao.getArtist(artistId).map { artistEntity ->
          ArtistUiModel(artistEntity.name, artistEntity.realName, artistEntity.aliases)
        }
      }
    }
  }
}