package com.example.thepersuader.module

import android.content.Context
import com.example.thepersuader.DiscogsApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {

    @Provides
    @Singleton
    internal fun provideContext() : Context{
        return DiscogsApplication.context
    }
}