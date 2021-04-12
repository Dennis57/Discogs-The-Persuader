package com.example.thepersuader.module

import android.content.Context
import com.example.thepersuader.DiscogsApplication
import com.example.thepersuader.room.DiscogsDatabase
import com.example.thepersuader.room.DiscogsDatabaseDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    internal fun provideDiscogsDatabase(context: Context) : DiscogsDatabase{
        return DiscogsDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    internal fun provideDiscogsDatabaseDao(discogsDatabase: DiscogsDatabase): DiscogsDatabaseDao {
        return discogsDatabase.discogsDatabaseDao
    }

}