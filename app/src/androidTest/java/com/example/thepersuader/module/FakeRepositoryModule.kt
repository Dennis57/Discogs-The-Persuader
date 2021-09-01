package com.example.thepersuader.module

import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.repository.*
import com.example.thepersuader.room.DiscogsDatabaseDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FakeRepositoryModule {

  @Provides
  @Singleton
  internal fun provideFakeArtistRepository() : ArtistRepositoryInterface {
    return FakeArtistRepository()
  }

  @Provides
  @Singleton
  internal fun provideFakeReleaseRepository() : ReleaseRepositoryInterface {
    return FakeReleaseRepository()
  }

  @Provides
  @Singleton
  internal fun provideFakeDetailReleaseRepository() : ReleaseDetailRepositoryInterface {
    return FakeReleaseDetailRepository()
  }
}