package com.example.thepersuader.module

import com.example.thepersuader.network.DiscogsApiService
import com.example.thepersuader.repository.ArtistRepository
import com.example.thepersuader.repository.ReleaseRepository
import com.example.thepersuader.room.DiscogsDatabaseDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideArtistRepository(discogsDatabaseDao: DiscogsDatabaseDao, retrofitService: DiscogsApiService) : ArtistRepository{
        return ArtistRepository(discogsDatabaseDao, retrofitService)
    }

    @Provides
    @Singleton
    internal fun provideReleaseRepository(discogsDatabaseDao: DiscogsDatabaseDao, retrofitService: DiscogsApiService) : ReleaseRepository{
        return ReleaseRepository(discogsDatabaseDao, retrofitService)
    }
}