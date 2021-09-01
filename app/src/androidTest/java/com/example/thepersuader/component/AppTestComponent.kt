package com.example.thepersuader.component

import android.app.Application
import com.example.thepersuader.DiscogsTestApplication
import com.example.thepersuader.module.*
import com.example.thepersuader.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
  modules = [
    ViewModelModule::class,
    ActivityModule::class,
    AndroidSupportInjectionModule::class,
    FakeRepositoryModule::class,
    FakeContextModule::class]
)
@Singleton
interface AppTestComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppTestComponent
  }

  fun inject(discogsTestApplication: DiscogsTestApplication)
}