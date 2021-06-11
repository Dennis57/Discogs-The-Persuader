package com.example.thepersuader.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thepersuader.main.MainViewModel
import com.example.thepersuader.ViewModelFactory
import com.example.thepersuader.ViewModelKey
import com.example.thepersuader.releaseDetail.ReleaseDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReleaseDetailViewModel::class)
    protected abstract fun releaseDetailViewModel(releaseDetailViewModel: ReleaseDetailViewModel): ViewModel
}