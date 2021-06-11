package com.example.thepersuader.repository

import com.example.thepersuader.model.release.ReleaseUiModel
import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.room.DiscogsDatabaseDao
import com.example.thepersuader.room.ReleaseEntity
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class ReleaseRepository @Inject constructor(
  private val discogsDatabaseDao: DiscogsDatabaseDao, private val retrofitService: DiscogsApiService
) {

  private var page = 1

  var releaseEntities: MutableList<ReleaseEntity> = mutableListOf()

  fun getReleases(artistId: Int): Observable<List<ReleaseUiModel>> {

    return retrofitService.getReleases(page, 20).onErrorReturnItem(
      Response.success(null)
    ).flatMap {
      it.body()?.let { releaseResponse ->
        for (response in releaseResponse.releases) {
          val releaseEntity = ReleaseEntity(
            response.id ?: 0, artistId, response.name.orEmpty(), response.year ?: 0
          )
          releaseEntities.add(releaseEntity)
        }
        discogsDatabaseDao.insertRelease(releaseEntities)

        page += 1
        val releaseUiModels = releaseEntities.map { entity ->
          ReleaseUiModel(entity.id, entity.name, entity.year)
        }
        Observable.just(releaseUiModels)
      } ?: kotlin.run {
        discogsDatabaseDao.getReleases(artistId).map { entities ->
          entities.map { entity ->
            ReleaseUiModel(entity.id, entity.name, entity.year)
          }
        }
      }
    }
  }
}