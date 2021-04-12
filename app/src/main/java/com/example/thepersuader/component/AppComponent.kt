package com.example.thepersuader.component

import android.app.Application
import com.example.thepersuader.DiscogsApplication
import com.example.thepersuader.module.*
import com.example.thepersuader.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ApiModule::class,
        DbModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class,
        RepositoryModule::class,
        ContextModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(discogsApplication: DiscogsApplication)
}