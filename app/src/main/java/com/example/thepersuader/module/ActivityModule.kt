package com.example.thepersuader.module

import com.example.thepersuader.main.MainActivity
import com.example.thepersuader.releaseDetail.ReleaseDetailActivity
import com.example.thepersuader.releaseDetail.TrackListFragment
import com.example.thepersuader.releaseDetail.VideoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun contributeReleaseDetailActivity(): ReleaseDetailActivity

    @ContributesAndroidInjector()
    abstract fun contributeTracklistFragment(): TrackListFragment

    @ContributesAndroidInjector()
    abstract fun contributeVideoFragment(): VideoFragment
}