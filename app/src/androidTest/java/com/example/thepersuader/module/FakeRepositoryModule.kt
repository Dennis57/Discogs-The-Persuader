package com.example.thepersuader.module

import com.example.thepersuader.repository.*
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