package com.example.thepersuader.module

import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.repository.*
import com.example.thepersuader.room.DiscogsDatabaseDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideArtistRepository(discogsDatabaseDao: DiscogsDatabaseDao, retrofitService: DiscogsApiService) : ArtistRepositoryInterface{
        return ArtistRepository(discogsDatabaseDao, retrofitService)
    }

    @Provides
    @Singleton
    internal fun provideReleaseRepository(discogsDatabaseDao: DiscogsDatabaseDao, retrofitService: DiscogsApiService) : ReleaseRepositoryInterface{
        return ReleaseRepository(discogsDatabaseDao, retrofitService)
    }

    @Provides
    @Singleton
    internal fun provideReleaseDetailRepository(discogsDatabaseDao: DiscogsDatabaseDao, retrofitService: DiscogsApiService) : ReleaseDetailRepositoryInterface{
        return ReleaseDetailRepository(discogsDatabaseDao, retrofitService)
    }
}