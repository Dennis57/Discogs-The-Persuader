package com.example.thepersuader.repository

import android.util.Log
import com.example.thepersuader.model.artist.ArtistUiModel
//import com.example.thepersuader.network.DiscogsApi
import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.room.ArtistEntity
import com.example.thepersuader.room.DiscogsDatabaseDao
import io.reactivex.Observable
import javax.inject.Inject

class ArtistRepository @Inject constructor(private val discogsDatabaseDao: DiscogsDatabaseDao, private val retrofitService: DiscogsApiService) {

    fun getArtist(artistId: Int): Observable<ArtistUiModel> {
        Log.e("<Error>", "Getting artist data")

        var artistResult: Observable<ArtistUiModel>

        try {
            artistResult = retrofitService.getArtist()
                .flatMap {
                    if (it.isSuccessful) {
                        Log.e("<Error>", "Getting artist data success")
                        it.body()?.let { artistResponse ->
                            val aliasesList: String =
                                artistResponse.aliases?.map { alias -> alias.name }
                                    ?.joinToString(", ") ?: "Unspecified"

                            val artistEntity = ArtistEntity(
                                artistResponse.id ?: 0,
                                artistResponse.name.orEmpty(),
                                artistResponse.real_name.orEmpty(),
                                aliasesList
                            )
                            discogsDatabaseDao.insertArtist(artistEntity)
                            Log.e("<Error>", "Successfully inserted artist")
                        }
                    }
                    Log.e("<Error>", "getArtists from database:" + discogsDatabaseDao.getArtist(artistId))
                    discogsDatabaseDao.getArtist(artistId).map { artistEntity ->
                        Log.e("<Error>", "Returning artist online")
                        ArtistUiModel(artistEntity.name, artistEntity.realName, artistEntity.aliases)
                    }
                }
        } catch (e: Exception) {
            artistResult = discogsDatabaseDao.getArtist(artistId).map { artistEntity ->
                Log.e("<Error>", "Returning artist offline")
                ArtistUiModel(artistEntity.name, artistEntity.realName, artistEntity.aliases)
            }
        }
        return artistResult
    }

}