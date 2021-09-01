package com.example.thepersuader.module

import android.content.Context
import com.example.thepersuader.DiscogsTestApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FakeContextModule {
  @Provides
  @Singleton
  internal fun provideContext() : Context {
    return DiscogsTestApplication.context
  }
}