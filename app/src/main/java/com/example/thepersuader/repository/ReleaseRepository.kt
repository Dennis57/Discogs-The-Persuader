package com.example.thepersuader.repository

import android.util.Log
import com.example.thepersuader.model.release.ReleaseUiModel
//import com.example.thepersuader.network.DiscogsApi
import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.room.DiscogsDatabaseDao
import com.example.thepersuader.room.ReleaseEntity
import io.reactivex.Observable
import javax.inject.Inject

class ReleaseRepository @Inject constructor(private val discogsDatabaseDao: DiscogsDatabaseDao, private val retrofitService: DiscogsApiService) {

    private var page = 1

    var releaseEntities: MutableList<ReleaseEntity> = mutableListOf()

    fun getReleases(artistId: Int) : Observable<List<ReleaseUiModel>> {

        Log.e("<Error>", "page: $page")
        return retrofitService.getReleases(page, 20)
            .flatMap {
                if(it.isSuccessful) {
                    it.body()?.let { releaseResponse ->
                        for (response in releaseResponse.releases) {
                            val releaseEntity = ReleaseEntity(
                                response.id?:0,
                                artistId,
                                response.name.orEmpty(),
                                response.year ?: 0
                            )
                            releaseEntities.add(releaseEntity)
                        }
                        Log.e("<Error>", "Release entities: ${releaseEntities.size}")
                        discogsDatabaseDao.insertRelease(releaseEntities)
                    }

                    page += 1
                }
                discogsDatabaseDao.getReleases(artistId).map { entities ->
                    entities.map { entity ->
                        ReleaseUiModel(entity.id, entity.name, entity.year)
                    }
                }
            }
    }
}